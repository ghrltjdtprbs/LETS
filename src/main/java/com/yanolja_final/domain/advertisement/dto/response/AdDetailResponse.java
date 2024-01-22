package com.yanolja_final.domain.advertisement.dto.response;

import com.yanolja_final.domain.advertisement.entity.Advertisement;
import com.yanolja_final.domain.packages.entity.Package;
import java.util.List;
import java.util.stream.Collectors;

public record AdDetailResponse(
    Long adId,
    String name,
    List<AdPackageResponse> packages
) {

    public static AdDetailResponse from(Advertisement ad, List<Package> packages) {
        return new AdDetailResponse(
            ad.getId(),
            ad.getName(),
            packages.stream().map(AdPackageResponse::from).limit(15).collect(Collectors.toList())
        );
    }
}
