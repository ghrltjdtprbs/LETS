package com.yanolja_final.domain.packages.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanolja_final.domain.order.exception.MaximumCapacityExceededException;
import com.yanolja_final.domain.packages.dto.response.PackageScheduleResponse;
import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.exception.PackageDateNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageDepartureOptionNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageNotFoundException;
import com.yanolja_final.domain.packages.repository.PackageDepartureOptionRepository;
import com.yanolja_final.domain.packages.repository.PackageQueryRepository;
import com.yanolja_final.domain.packages.repository.PackageRepository;
import com.yanolja_final.domain.search.controller.response.SearchedPackageCountResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PackageService {

    private final PackageRepository packageRepository;
    private final PackageQueryRepository packageQueryRepository;
    private final PackageDepartureOptionRepository packageDepartureOptionRepository;

    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    // Package
    public Package getPackageWithIncrementPurchasedCount(Long id) {
        Package aPackage = findById(id);
        aPackage.plusPurchasedCount();
        return packageRepository.save(aPackage);
    }

    public Package findById(Long id) {
        return packageRepository.findById(id)
            .orElseThrow(PackageNotFoundException::new);
    }

    public Page<Package> findAll(Pageable pageable) {
        return packageRepository.findAll(pageable);
    }

    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    public Page<Package> findAllByViewedCount(
        Pageable pageable, String continentName, String nationName
    ) {
        boolean filterByNation = !"전체".equals(nationName);
        boolean filterByContinent = !"전체".equals(continentName);

        String jpql = "SELECT p FROM Package p ";
        String countJpql = "SELECT COUNT(p) FROM Package p ";

        if (filterByNation || filterByContinent) {
            jpql += "JOIN p.nation n JOIN p.continent c ";
            countJpql += "JOIN p.nation n JOIN p.continent c ";
        }

        String whereClause = "WHERE (1=1) ";

        if (filterByNation) {
            whereClause += "AND n.name = :nationName ";
        }

        if (filterByContinent) {
            whereClause += "AND c.name = :continentName ";
        }

        jpql += whereClause + "ORDER BY p.viewedCount DESC";
        countJpql += whereClause;

        TypedQuery<Package> query = entityManager.createQuery(jpql, Package.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);

        if (filterByNation) {
            query.setParameter("nationName", nationName);
            countQuery.setParameter("nationName", nationName);
        }
        if (filterByContinent) {
            query.setParameter("continentName", continentName);
            countQuery.setParameter("continentName", continentName);
        }

        int totalRows = countQuery.getSingleResult().intValue();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Package> packages = query.getResultList();

        return new PageImpl<>(packages, pageable, totalRows);
    }

    public Page<Package> findAllByPurchasedCount(Pageable pageable) {
        return packageRepository.findAllByOrderByMonthlyPurchasedCountDesc(pageable);
    }

    public List<PackageScheduleResponse> getSchedulesById(Long packageId) {
        Package aPackage = findById(packageId);
        try {
            return List.of(
                objectMapper.readValue(aPackage.getSchedules(), PackageScheduleResponse[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewed(Package aPackage) {
        aPackage.viewed();
        packageRepository.save(aPackage);
    }


    public SearchedPackageCountResponse getFilteredPackageCount(
        Integer minPrice,
        Integer maxPrice,
        String hashtags,
        String nations,
        String continents
    ) {
        Long result =
            packageQueryRepository.countByAdultPriceRangeAndFilters(
                minPrice,
                maxPrice,
                slideString(nations),
                slideString(continents),
                slideString(hashtags)
            );
        return SearchedPackageCountResponse.from(Math.toIntExact(result));
    }

    public Page<Package> getFilteredPackage(
        Integer minPrice,
        Integer maxPrice,
        List<Hashtag> hashtags,
        String nations,
        String continents,
        String sortBy,
        Pageable pageable
    ) {

        String[] hashtagNames =
            (hashtags != null && !hashtags.isEmpty()) ? hashtags.stream().map(Hashtag::getName)
                .toArray(String[]::new) : null;

        Page<Package> responsePage =
            packageQueryRepository.packageInfoByAdultPriceRangeAndFilters(
                minPrice,
                maxPrice,
                slideString(nations),
                slideString(continents),
                hashtagNames,
                sortBy,
                pageable
            );
        return responsePage;
    }

    private String[] slideString(String str) {
        if (str == null) {
            return null;
        }
        return str.split(",");
    }

    public Page<Package> getPackagesByHashtag(Hashtag hashtag, String sortBy, Pageable pageable) {
        List<Package> byHashtagAndSort =
            packageRepository.findByHashtagAndSort(hashtag.getName(), sortBy);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), byHashtagAndSort.size());

        List<Package> pageContent = byHashtagAndSort.subList(start, end);
        return new PageImpl<>(pageContent, pageable, byHashtagAndSort.size());
    }

    // PackageDepartureOption
    public void updateCurrentPeopleWithOrder(Long id, Long packageId, int orderTotalPeople) {
        PackageDepartureOption packageDepartureOption = findByDepartureOptionId(id);

        packageDepartureOptionRepository.findByPackageIdAndDepartureOptionId(packageId, id)
            .orElseThrow(PackageDateNotFoundException::new);

        if (packageDepartureOption.getIncrementCurrentReservationCount(orderTotalPeople)
            > packageDepartureOption.getMaxReservationCount()) {
            throw new MaximumCapacityExceededException();
        }
        packageDepartureOptionRepository.save(packageDepartureOption);
    }

    public PackageDepartureOption findByDepartureOptionId(Long id) {
        return packageDepartureOptionRepository.findById(id).orElseThrow(
            PackageDepartureOptionNotFoundException::new);
    }

    public List<Package> findAllByContinent(Continent continent) {
        return packageRepository.findAllByContinent(continent);
    }

    public List<Package> findAllByNation(Nation nation) {
        return packageRepository.findAllByNation(nation);
    }

    public Page<Package> findSimilarPackages(Package basePackage, Pageable pageable) {
        return packageRepository.findSimilarPackages(basePackage, pageable);
    }
}
