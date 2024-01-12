package com.yanolja_final.domain.order.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.packages.entity.Package;

public record OrderResponse(
    String orderCode,
    @JsonProperty("package")
    OrderPackageResponse aPackage
) {

    public static OrderResponse from(
        Order order,
        Package aPackage,
        String packageIntroImageUrl,
        boolean isWish,
        boolean isReviewed
    ) {
        return new OrderResponse(
            order.getCode(),
            OrderPackageResponse.from(aPackage, order.getAvailableDateId(), packageIntroImageUrl,
                isWish, isReviewed)
        );
    }
}
