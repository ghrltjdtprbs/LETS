package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.Package;
import java.util.List;

public record PackageListItemResponse(
    Long packageId,
    String imageUrl,
    String nationName,
    List<String> hashtags,
    int minPrice,
    int lodgeDays,
    int tripDays
) {

    public static PackageListItemResponse from(Package aPackage) {
        return new PackageListItemResponse(
            aPackage.getId(),
            aPackage.getThumbnailImageUrl(),
            aPackage.getNationName(),
            aPackage.getHashtagNames(),
            aPackage.getMinPrice(),
            aPackage.getLodgeDays(),
            aPackage.getTripDays()
        );
    }
}
