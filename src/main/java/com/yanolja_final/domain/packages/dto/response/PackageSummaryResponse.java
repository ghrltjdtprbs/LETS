package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.review.entity.Review;
import java.util.List;

public record PackageSummaryResponse(
    String title,
    String imageUrl,
    int price,
    int hotelStars,
    List<String> schedules,
    int shoppingCount,
    List<String> hashtags,
    int lodgeDays,
    int tripDays,
    int reservationCount,
    int minReservationCount,
    int reviewCount,
    double averageStars,
    int purchasedCount
) {

    public static PackageSummaryResponse from(Package aPackage, PackageDepartureOption departureOption, int reviewCount, double averageStars, List<String> schedules) {
        return new PackageSummaryResponse(
            aPackage.getTitle(),
            aPackage.getThumbnailImageUrl(),
            aPackage.getMinPrice(),
            aPackage.getHotelStars(),
            schedules,
            aPackage.getShoppingCount(),
            aPackage.getHashtagNames(),
            aPackage.getLodgeDays(),
            aPackage.getTripDays(),
            departureOption.getCurrentReservationCount(),
            departureOption.getMinReservationCount(),
            reviewCount,
            averageStars,
            aPackage.getMonthlyPurchasedCount()
        );
    }
}
