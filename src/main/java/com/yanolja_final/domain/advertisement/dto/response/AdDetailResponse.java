package com.yanolja_final.domain.advertisement.dto.response;

import com.yanolja_final.domain.advertisement.entity.Advertisement;
import com.yanolja_final.domain.advertisement.entity.AdvertisementImage;
import java.util.List;
import java.util.stream.Collectors;

public record AdDetailResponse(
    Long adId,
    String name,
    List<String> imageUrls,
    List<AdPackageResponse> packages
) {

    public static AdDetailResponse from(Advertisement ad) {
        return new AdDetailResponse(
            ad.getId(),
            ad.getName(),
            ad.getImages().stream().map(AdvertisementImage::getImageUrl).collect(Collectors.toList()),
            ad.getPackages().stream().map(AdPackageResponse::from).collect(Collectors.toList())
        );
    }
}
