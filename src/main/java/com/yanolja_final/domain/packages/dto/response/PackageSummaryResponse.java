package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import java.util.List;

public record PackageSummaryResponse(
    String title,
    String imageUrl,
    int price,
    int hotelStars,
    List<PackageScheduleResponse> schedules,
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

    public static PackageSummaryResponse from(Package aPackage,
        PackageDepartureOption departureOption, int reviewCount, double averageStars,
        List<PackageScheduleResponse> schedules) {
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
            Math.round(averageStars * 10) / 10.0,
            aPackage.getMonthlyPurchasedCount()
        );
    }
}
