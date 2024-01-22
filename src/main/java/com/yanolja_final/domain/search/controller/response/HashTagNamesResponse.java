package com.yanolja_final.domain.search.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanolja_final.domain.packages.entity.Hashtag;
import java.util.List;
import java.util.stream.Collectors;

public record HashTagNamesResponse(
    @JsonProperty("hashtags")
    List<String> names
) {

    public static HashTagNamesResponse from(List<Hashtag> hashtags){
        return new HashTagNamesResponse(
            hashtags.stream()
                .map(Hashtag::getName)
                .collect(Collectors.toList())
        );
    }
}
