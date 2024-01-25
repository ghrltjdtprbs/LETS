package com.yanolja_final.domain.review.repository;

import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.review.entity.Review;
import com.yanolja_final.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByUserId(Long userId, Pageable pageable);

    List<Review> findByPackageId(Long packageId);

    Page<Review> findByPackageId(Long packageId, Pageable pageable);

    boolean existsByUserAndOrder(User user, Order order);
}
