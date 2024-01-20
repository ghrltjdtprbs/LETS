package com.yanolja_final.domain.search.controller.response;

import com.yanolja_final.domain.packages.dto.response.ContinentResponse;
import com.yanolja_final.domain.packages.dto.response.NationResponse;
import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Nation;
import java.util.List;
import java.util.stream.Collectors;

public record ContinentNationResponse(
    List<NationResponse> nations,
    List<ContinentResponse> continents
) {

    public static ContinentNationResponse from(List<Nation> nations, List<Continent> continents) {
        return new ContinentNationResponse(
            nations.stream()
                .map(NationResponse::from)
                .collect(Collectors.toList()),
            continents.stream()
                .map(ContinentResponse::from)
                .collect(Collectors.toList())
        );
    }
}
