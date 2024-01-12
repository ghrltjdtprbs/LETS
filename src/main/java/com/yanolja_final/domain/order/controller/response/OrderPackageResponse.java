package com.yanolja_final.domain.order.controller.response;

import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.entity.Package;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderPackageResponse(
    Long packageId,
    Long availableDateId,
    String imageUrl,
    String nationName,
    String title,
    Set<String> hashtags,
    Integer minPrice,
    Integer lodgeDays,
    Integer tripDays,
    Boolean isWish,
    Boolean isReviewed
) {

    public static OrderPackageResponse from(
        Package aPackage,
        Long availableDateId,
        String packageIntroImage,
        Boolean isWish,
        Boolean isReviewed
    ) {

        return new OrderPackageResponse(
            aPackage.getId(),
            availableDateId,
            packageIntroImage,
            aPackage.getNationName(),
            aPackage.getTitle(),
            aPackage.getHashtags().stream()
                .map(Hashtag::getName)
                .collect(Collectors.toSet()),
            aPackage.getMinPrice(),
            aPackage.getLodgeDays(),
            aPackage.getTripDays(),
            isWish,
            isReviewed
        );
    }
}
