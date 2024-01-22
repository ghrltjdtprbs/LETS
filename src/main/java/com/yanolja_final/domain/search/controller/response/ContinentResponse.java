package com.yanolja_final.domain.search.controller.response;

import com.yanolja_final.domain.packages.entity.Continent;

public record ContinentResponse(
    String name,
    String imageUrl
) {

    public static ContinentResponse from(Continent continent) {
        return new ContinentResponse(
            continent.getName(),
            continent.getImageUrl()
        );
    }
}
