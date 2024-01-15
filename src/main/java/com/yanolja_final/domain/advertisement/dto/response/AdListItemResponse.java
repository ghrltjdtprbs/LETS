package com.yanolja_final.domain.advertisement.dto.response;

import com.yanolja_final.domain.advertisement.entity.Advertisement;

public record AdListItemResponse(
    Long adId,
    String imageUrl
) {

    public static AdListItemResponse from(Advertisement advertisement) {
        return new AdListItemResponse(
            advertisement.getId(),
            advertisement.getImageUrl()
        );
    }
}
