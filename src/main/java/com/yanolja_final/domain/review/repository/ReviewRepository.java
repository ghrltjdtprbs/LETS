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

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r " +
        "WHERE r.packageId = :packageId AND r.user.id = :userId")
    boolean isUserReviewedPackage(@Param("userId") Long userId, @Param("packageId") Long packageId);

    @Query("SELECT r FROM Review r WHERE r.order.aPackage.id = :packageId")
    List<Review> findReviewsByPackageIdSummary(@Param("packageId") Long packageId);

    @Query("SELECT r FROM Review r WHERE r.order.aPackage.id = :packageId")
    Page<Review> findReviewsByPackageId(@Param("packageId") Long packageId, Pageable pageable);
}
