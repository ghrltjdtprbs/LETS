package com.yanolja_final.domain.packages.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanolja_final.domain.order.exception.MaximumCapacityExceededException;
import com.yanolja_final.domain.packages.dto.response.PackageScheduleResponse;
import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.exception.PackageDateNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageDepartureOptionNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageNotFoundException;
import com.yanolja_final.domain.packages.repository.PackageDepartureOptionRepository;
import com.yanolja_final.domain.packages.repository.PackageQueryRepository;
import com.yanolja_final.domain.packages.repository.PackageRepository;
import com.yanolja_final.domain.search.controller.response.SearchedPackageCountResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Package> findAllByViewedCount(Pageable pageable) {
        return packageRepository.findAllByOrderByViewedCountDesc(pageable);
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


    public SearchedPackageCountResponse getFilteredPackageCount(Integer minPrice, Integer maxPrice,
        String hashtags,
        String nations, String continents) {
        Integer count = packageQueryRepository.countByAdultPriceRangeAndFilters(minPrice, maxPrice,
            slideString(nations),
            slideString(continents), slideString(hashtags));
        return SearchedPackageCountResponse.from(count);
    }

    private String[] slideString(String str) {
        if (str == null) {
            return null;
        }
        return str.split(",");
    }

    public Page<Package> getPackagesByHashtag(Hashtag hashtag, String sortBy, Pageable pageable) {
        Sort sort = Sort.by("departureTime").ascending();
        if ("price_desc".equals(sortBy)) {
            sort = Sort.by("price").descending();
        } else if ("price_asc".equals(sortBy)) {
            sort = Sort.by("price").ascending();
        }
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return packageRepository.findByHashtagsContains(hashtag, pageable);
    }

    // PackageDepartureOption
    public void updateCurrentPeopleWithOrder(Long id, Long packageId, int orderTotalPeople) {
        PackageDepartureOption packageDepartureOption = findByDepartureOptionId(id);

        packageDepartureOptionRepository.findByPackageIdAndDepartureOptionId(packageId, id)
            .orElseThrow(
                PackageDateNotFoundException::new
            );

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

}
