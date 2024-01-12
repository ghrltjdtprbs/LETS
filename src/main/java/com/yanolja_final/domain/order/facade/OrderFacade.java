package com.yanolja_final.domain.order.facade;

import com.yanolja_final.domain.order.controller.request.OrderCreateRequest;
import com.yanolja_final.domain.order.controller.response.OrderCreateResponse;
import com.yanolja_final.domain.order.controller.response.OrderResponse;
import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.order.service.OrderService;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.review.service.ReviewService;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import com.yanolja_final.domain.wish.service.WishService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderFacade {

    private final OrderService orderService;
    private final UserService userService;
    private final PackageService packageService;
    private final WishService wishService;
    private final ReviewService reviewService;

    public Page<OrderResponse> read(Long userId, Pageable pageable) {
        User user = userService.findActiveUserById(userId);
        Page<Order> ordersPage = orderService.read(user, pageable);
        List<OrderResponse> responses = ordersPage.getContent().stream()
            .map(order -> {
                Package aPackage = order.getAPackage();
                String firstIntroImageUrl = aPackage.getIntroImages().get(0).getImageUrl();

                boolean isWish = wishService.isUserWishingPackage(order.getId(), aPackage.getId());
                boolean isReviewed =
                    reviewService.isUserReviewedPackage(order.getId(), aPackage.getId());

                return OrderResponse.from(order, aPackage, firstIntroImageUrl, isWish, isReviewed);
            })
            .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, ordersPage.getTotalElements());
    }

    @Transactional
    public OrderCreateResponse create(Long userId, OrderCreateRequest request) {
        User user = userService.findActiveUserById(userId);
        Package aPackage = packageService.getPackageWithIncrementPurchasedCount(request.packageId());
        packageService.updateCurrentPeopleWithOrder(
            request.availableDateId(),
            request.packageId(),
            request.totalCount()
        );
        return orderService.create(user, aPackage, request);
    }
}
