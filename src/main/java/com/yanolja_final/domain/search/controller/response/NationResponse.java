package com.yanolja_final.domain.search.controller.response;

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
