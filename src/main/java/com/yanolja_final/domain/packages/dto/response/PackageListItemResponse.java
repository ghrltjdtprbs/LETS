package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.Package;
import java.util.List;

public record PackageListItemResponse(
    Long packageId,
    String imageUrl,
    String nationName,
    String title,
    List<String> hashtags,
    int minPrice,
    boolean isWish,
    int lodgeDays,
    int tripDays
) {

    public static PackageListItemResponse from(Package aPackage, boolean isWish) {
        return new PackageListItemResponse(
            aPackage.getId(),
            aPackage.getThumbnailImageUrl(),
            aPackage.getNationName(),
            aPackage.getTitle(),
            aPackage.getHashtagNames(),
            aPackage.getMinPrice(),
            isWish,
            aPackage.getLodgeDays(),
            aPackage.getTripDays()
        );
    }
}
