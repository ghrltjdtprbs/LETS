package com.yanolja_final.domain.order.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import java.time.LocalDate;

public record OrderResponse(
    String orderCode,
    Long availableDateId,
    @JsonProperty("package")
    OrderPackageResponse aPackage
) {

    public static OrderResponse from(
        Order order,
        LocalDate departureDate,
        boolean isWish,
        boolean isReviewed
    ) {
        return new OrderResponse(
            order.getCode(),
            order.getAvailableDateId(),
            OrderPackageResponse.from(order,departureDate,isWish, isReviewed)
        );
    }
}
