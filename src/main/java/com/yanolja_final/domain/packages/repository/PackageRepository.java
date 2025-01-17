package com.yanolja_final.domain.packages.repository;

import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.entity.Package;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PackageRepository extends JpaRepository<Package, Long> {

    Page<Package> findAllByOrderByViewedCountDesc(Pageable pageable);

    Page<Package> findAllByOrderByMonthlyPurchasedCountDesc(Pageable pageable);

    List<Package> findAllByOrderByMonthlyPurchasedCountDesc();

    List<Package> findAllByContinent(Continent continent);

    List<Package> findAllByNation(Nation nation);

    /**
     * @return p와 공통 해시태그를 많이 가진 순으로 정렬된 p와 같은 나라인 다른 패키지들
     */
    @Query("SELECT p2"
        + " FROM Package p1"
        + " JOIN p1.hashtags h1"
        + " JOIN h1.packages p2"
        + " WHERE p1 = :p"
        + " AND p2.nation = p1.nation"
        + " AND p2 <> p1"
        + " AND h1 IN (SELECT h FROM Package p3 JOIN p3.hashtags h WHERE p3 = p2)"
        + " GROUP BY p2.id"
        + " ORDER BY COUNT(h1) DESC")
    Page<Package> findSimilarPackages(Package p, Pageable pageable);

    @Query("SELECT p FROM Package p " +
        "LEFT JOIN FETCH p.availableDates pdo " +
        "WHERE pdo.adultPrice = (SELECT MIN(pdo2.adultPrice) FROM PackageDepartureOption pdo2 WHERE pdo2.aPackage.id = p.id) "
        +
        "AND :hashtag IN (SELECT h2.name FROM p.hashtags h2) " +
        "ORDER BY " +
        "CASE WHEN :sortBy = 'departure_date' THEN pdo.departureDate END ASC, " +
        "CASE WHEN :sortBy = 'price_desc' THEN pdo.adultPrice END DESC, " +
        "CASE WHEN :sortBy = 'price_asc' THEN pdo.adultPrice END ASC")
    List<Package> findByHashtagAndSort(
        @Param("hashtag") String hashtagName,
        @Param("sortBy") String sortBy
    );
}
