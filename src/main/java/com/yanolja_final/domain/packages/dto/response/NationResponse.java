package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.packages.entity.Nation;

public record NationResponse(
    String name,
    String imageUrl
) {

    public static NationResponse from(Nation nation) {
        return new NationResponse(
            nation.getName(),
            nation.getImageUrl()
        );
    }
}
