package com.yanolja_final.domain.order.facade;

import com.yanolja_final.domain.order.controller.request.OrderCreateRequest;
import com.yanolja_final.domain.order.controller.response.OrderCreateResponse;
import com.yanolja_final.domain.order.service.OrderService;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final UserService userService;
    private final PackageService packageService;

    @Transactional
    public OrderCreateResponse create(Long userId, OrderCreateRequest request) {
        User user = userService.findActiveUserById(userId);
        Package aPackage =
            packageService.getPackageWithIncrementPurchasedCount(request.packageId());
        packageService.updateCurrentPeopleWithOrder(
            request.availableDateId(),
            request.packageId(),
            request.totalCount()
        );
        return orderService.create(user, aPackage, request);
    }
}
