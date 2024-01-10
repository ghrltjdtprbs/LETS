package com.yanolja_final.domain.packages.repository;

import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PackageDepartureOptionRepository
    extends JpaRepository<PackageDepartureOption, Long> {

    @Query("SELECT p FROM PackageDepartureOption p WHERE p.aPackage.id = :packageId AND p.id = :departureOptionId")
    Optional<PackageDepartureOption> findByPackageIdAndDepartureOptionId(@Param("packageId") Long packageId, @Param("departureOptionId") Long departureOptionId);
}
