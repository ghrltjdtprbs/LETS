package com.yanolja_final.domain.advertisement.dto.response;

import com.yanolja_final.domain.packages.entity.Package;

public record AdPackageResponse(
    Long packageId,
    String imageUrl,
    String transportation,
    String title,
    int minPrice
) {

    public static AdPackageResponse from(Package aPackage) {
        return new AdPackageResponse(
            aPackage.getId(),
            aPackage.getThumbnailImageUrl(),
            aPackage.getTransportation(),
            aPackage.getTitle(),
            aPackage.getMinPrice()
        );
    }
}
