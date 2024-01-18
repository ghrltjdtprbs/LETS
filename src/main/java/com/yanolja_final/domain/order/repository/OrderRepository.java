package com.yanolja_final.domain.order.repository;

import com.yanolja_final.domain.order.entity.Order;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = DATE(:targetDate)")
    long countOrdersByCreatedAt(@Param("targetDate") LocalDate targetDate);
}