package com.yanolja_final.domain.order.controller.response;

import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.packages.entity.Package;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record OrderPackageResponse(
    Long packageId,
    String imageUrl,
    String nationName,
    String title,
    List<String> hashtags,
    Integer lodgeDays,
    Integer tripDays,
    String travelPeriod,
    Boolean isWish,
    Boolean isReviewed
) {

    public static OrderPackageResponse from(
        Order order,
        LocalDate departureDate,
        Boolean isWish,
        Boolean isReviewed
    ) {

        return new OrderPackageResponse(
            order.getAPackage().getId(),
            order.getAPackage().getImageUrls().get(0),
            order.getAPackage().getNationName(),
            order.getAPackage().getTitle(),
            order.getAPackage().getHashtagNames(),
            order.getAPackage().getLodgeDays(),
            order.getAPackage().getTripDays(),
            calcTravelPeriod(departureDate, order.getAPackage().getTripDays()),
            isWish,
            isReviewed
        );
    }

    private static String calcTravelPeriod(LocalDate departDate, Integer tripDays) {

        if (tripDays <= 0 || departDate == null) {
            return null;
        }
        LocalDate endDate = departDate.plusDays(tripDays - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");

        String startDateString = departDate.format(formatter);
        String endDateString = endDate.format(formatter);

        return startDateString + "~" + endDateString;
    }
}
