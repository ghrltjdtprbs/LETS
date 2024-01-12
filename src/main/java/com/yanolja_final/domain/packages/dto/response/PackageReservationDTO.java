package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.PackageDepartureOption;

public record PackageReservationDTO(
    int current,
    int remain,
    int min
) {

    public static PackageReservationDTO from(PackageDepartureOption departOption) {
        return new PackageReservationDTO(
            departOption.getCurrentReservationCount(),
            departOption.getRemainReservationCount(),
            departOption.getMinReservationCount()
        );
    }
}
