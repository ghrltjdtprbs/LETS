package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import java.time.format.DateTimeFormatter;

public record PackageAvailableDateResponse(
    Long availableDateId,
    String date,
    Integer adultPrice,
    Integer lodgeDays,
    Integer tripDays
) {

    public static PackageAvailableDateResponse from(PackageDepartureOption option) {
        return new PackageAvailableDateResponse(
            option.getId(),
            option.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            option.getAdultPrice(),
            option.getLodgeDays(),
            option.getTripDays()
        );
    }
}
