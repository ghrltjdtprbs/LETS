package com.yanolja_final.domain.wish.repository;

import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.wish.entity.Wish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByIdAndUserId(Long wishId, Long userId);

    Page<Wish> findByUserId(Long userId, Pageable pageable);

    boolean existsByUserAndAPackage(User user, Package aPackage);

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM Wish w " +
        "WHERE w.user.id = :userId AND w.aPackage.id = :packageId")
    boolean isUserWishingPackage(@Param("userId") Long userId, @Param("packageId") Long packageId);
}
