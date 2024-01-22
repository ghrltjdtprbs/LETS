package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.PackageDepartureOption;

public record PackagePriceDTO(
    int adult,
    int infant,
    int baby
) {

    public static PackagePriceDTO from(PackageDepartureOption departOption) {
        return new PackagePriceDTO(
            departOption.getAdultPrice(),
            departOption.getInfantPrice(),
            departOption.getBabyPrice()
        );
    }
}
