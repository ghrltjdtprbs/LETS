package com.yanolja_final.domain.packages.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.user.entity.User;
import java.util.List;
import java.util.Optional;

public record PackageDetailResponse(
    Long packageId,
    List<String> hashtags,
    PackageDateTimeDTO departureDatetime,
    PackageDateTimeDTO endDatetime,
    List<String> imageUrls,
    String nationName,
    String continentName,
    String title,
    Double averageStars,
    PackagePriceDTO totalPrice,
    PackageReservationDTO reservation,
    String transportation,
    String[] info,
    List<String> introImageUrls,
    PackageServiceItemDTO[] inclusionList,
    PackageServiceItemDTO[] exclusionList,
    int lodgeDays,
    int tripDays,
    int reviewCount,
    MyInfoDto myInfo,
    boolean isWish
) {

    public static PackageDetailResponse from(Package aPackage, PackageDepartureOption departOption,
        User user, boolean isWish, double averageStars, int reviewCount) {
        PackageServiceItemDTO[] inclusionList = null;
        PackageServiceItemDTO[] exclusionList = null;
        try {
            inclusionList = new ObjectMapper().readValue(aPackage.getInclusionList(), PackageServiceItemDTO[].class);
            exclusionList = new ObjectMapper().readValue(aPackage.getInclusionList(), PackageServiceItemDTO[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new PackageDetailResponse(
            aPackage.getId(),
            aPackage.getHashtagNames(),
            PackageDateTimeDTO.from(departOption.getDepartureDate(), aPackage.getDepartureTime()),
            PackageDateTimeDTO.from(
                departOption.getDepartureDate().plusDays(aPackage.getTripDays() - 1), aPackage.getEndTime()
            ),
            aPackage.getImageUrls(),
            aPackage.getNationName(),
            aPackage.getContinentName(),
            aPackage.getTitle(),
            averageStars,
            PackagePriceDTO.from(departOption),
            PackageReservationDTO.from(departOption),
            aPackage.getTransportation(),
            aPackage.getInfo().split("\n"),
            aPackage.getIntroImageUrls(),
            inclusionList,
            exclusionList,
            aPackage.getLodgeDays(),
            aPackage.getTripDays(),
            reviewCount,
            MyInfoDto.from(user),
            isWish
        );
    }
}
