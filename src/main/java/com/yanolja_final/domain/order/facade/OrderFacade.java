package com.yanolja_final.domain.order.facade;

import com.yanolja_final.domain.order.controller.request.OrderCreateRequest;
import com.yanolja_final.domain.order.controller.response.OrderCreateResponse;
import com.yanolja_final.domain.order.controller.response.OrderResponse;
import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.order.exception.NoGuestsException;
import com.yanolja_final.domain.order.service.OrderService;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.review.service.ReviewService;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import com.yanolja_final.domain.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

        return ordersPage.map(o -> {
                PackageDepartureOption packageDepartureOption =
                    packageService.findByDepartureOptionId(o.getAvailableDateId());
                return OrderResponse.from(o, packageDepartureOption.getDepartureDate(),
                    wishService.isWish(user, o.getAPackage()),
                    reviewService.isReviewed(user, o));
            }
        );
    }

    @Transactional
    public OrderCreateResponse create(Long userId, OrderCreateRequest request) {
        if (
            request.numberOfPeople().adult() <= 0 && request.numberOfPeople().infant() <= 0
                && request.numberOfPeople().baby() <= 0
        ) {
            throw new NoGuestsException();
        }

        User user = userService.findActiveUserById(userId);
        Package aPackage =
            packageService.getPackageWithIncrementPurchasedCount(request.packageId());
        PackageDepartureOption packageDepartureOption =
            packageService.findByDepartureOptionId(request.availableDateId());

        packageService.updateCurrentPeopleWithOrder(
            request.availableDateId(),
            request.packageId(),
            request.totalCount()
        );
        return orderService.create(user, aPackage, packageDepartureOption, request);
    }
}
