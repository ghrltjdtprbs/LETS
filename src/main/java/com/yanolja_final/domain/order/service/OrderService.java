package com.yanolja_final.domain.order.service;

import com.yanolja_final.domain.order.controller.request.OrderCreateRequest;
import com.yanolja_final.domain.order.controller.response.OrderCreateResponse;
import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.order.exception.OrderNotFoundException;
import com.yanolja_final.domain.order.repository.OrderRepository;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.user.entity.User;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<Order> read(User user, Pageable pageable) {
        return orderRepository.findByUserId(user.getId(), pageable);
    }

    @Transactional
    public OrderCreateResponse create(
        User user, Package aPackage,
        PackageDepartureOption packageDepartureOption,
        OrderCreateRequest request
    ) {
        String code = generateDailyOrderCode();
        Order order = request.toEntity(user, aPackage, packageDepartureOption, code);
        orderRepository.save(order);
        return OrderCreateResponse.fromEntities(order, user);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(OrderNotFoundException::new);
    }

    private String generateDailyOrderCode() {
        LocalDate today = LocalDate.now();
        Long todayOrderCount = orderRepository.countOrdersByCreatedAt(today);

        String orderCode = String.format("%04d%02d%02d%05d",
            today.getYear(), today.getMonthValue(), today.getDayOfMonth(), todayOrderCount);
        return orderCode;
    }
}
