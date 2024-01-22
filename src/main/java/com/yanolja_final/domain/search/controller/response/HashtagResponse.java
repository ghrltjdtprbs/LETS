package com.yanolja_final.domain.search.controller.response;

import com.yanolja_final.domain.packages.entity.Hashtag;

public record HashtagResponse(
    String name,
    String imageUrl
) {

    public static HashtagResponse from(Hashtag hashtag) {
        return new HashtagResponse(
            hashtag.getName(),
            hashtag.getImageUrl()
        );
    }
}
