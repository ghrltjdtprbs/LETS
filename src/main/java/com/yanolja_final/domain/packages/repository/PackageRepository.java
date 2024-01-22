package com.yanolja_final.domain.packages.repository;

import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Hashtag;
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
    @Query("SELECT p FROM Package p " +
        "JOIN p.availableDates d " +
        "WHERE :hashtag member of p.hashtags " +
        "ORDER BY " +
        "CASE WHEN :sortBy = 'departure_date' THEN d.departureDate END ASC, " +
        "CASE WHEN :sortBy = 'price_desc' THEN d.adultPrice END DESC, " +
        "CASE WHEN :sortBy = 'price_asc' THEN d.adultPrice END ASC")
    Page<Package> findByHashtagAndSort(
        @Param("hashtag") Hashtag hashtag,
        @Param("sortBy") String sortBy,
        Pageable pageable
    );
}
