package com.yanolja_final.domain.review.repository;

import com.yanolja_final.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r LEFT JOIN r.order o LEFT JOIN o.aPackage p WHERE p.id = :packageId OR r.packageId = :packageId")
    List<Review> findReviewsByPackageIdSummary(@Param("packageId") Long packageId);

    @Query("SELECT r FROM Review r LEFT JOIN r.order o LEFT JOIN o.aPackage p WHERE p.id = :packageId OR r.packageId = :packageId")
    Page<Review> findReviewsByPackageId(@Param("packageId") Long packageId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r " +
        "WHERE r.aPackage.id = :packageId AND r.user.id = :userId")
    boolean isUserReviewedPackage(
        @Param("userId") Long userId,
        @Param("packageId") Long packageId
    );
}
