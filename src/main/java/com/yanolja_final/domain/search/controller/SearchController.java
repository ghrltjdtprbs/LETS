package com.yanolja_final.domain.search.controller;

import com.yanolja_final.domain.search.controller.response.ContinentNationResponse;
import com.yanolja_final.domain.search.controller.response.HashTagNamesResponse;
import com.yanolja_final.domain.search.controller.response.HashtagResponse;
import com.yanolja_final.domain.search.controller.response.SearchedPackageCountResponse;
import com.yanolja_final.domain.search.facade.SearchFacade;
import com.yanolja_final.global.config.argumentresolver.LoginedUserId;
import com.yanolja_final.global.util.PaginationUtils;
import com.yanolja_final.global.util.ResponseDTO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {

    private final SearchFacade searchFacade;

    @GetMapping
    public ResponseEntity<ResponseDTO<Map<String, Object>>> getFilteredPackage(
        @LoginedUserId Long userId,
        @RequestParam(name = "minPrice", required = false, defaultValue = "0") int minPrice,
        @RequestParam(name = "maxPrice", required = false, defaultValue = "" + Integer.MAX_VALUE) int maxPrice,
        @RequestParam(name = "hashtags", required = false) String hashtags,
        @RequestParam(name = "nations", required = false) String nations,
        @RequestParam(name = "continents", required = false) String continents,
        @RequestParam(required = false) String sortBy,
        Pageable pageable
    ) {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(
                PaginationUtils.createPageResponse(
                    searchFacade.getFilteredPackage(userId, minPrice, maxPrice, hashtags, nations,
                        continents, sortBy, pageable)
                )
            )
        );
    }

    @GetMapping("/count")
    public ResponseEntity<ResponseDTO<SearchedPackageCountResponse>> getFilteredPackageCount(
        @RequestParam(name = "minPrice", required = false, defaultValue = "0") int minPrice,
        @RequestParam(name = "maxPrice", required = false, defaultValue = "" + Integer.MAX_VALUE) int maxPrice,
        @RequestParam(name = "hashtags", required = false) String hashtags,
        @RequestParam(name = "nations", required = false) String nations,
        @RequestParam(name = "continents", required = false) String continents
    ) {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(
                searchFacade.getFilteredPackageCount(minPrice, maxPrice, hashtags, nations,
                    continents)
            )
        );
    }

    @GetMapping("/options/hashtags")
    public ResponseEntity<ResponseDTO<List<HashtagResponse>>> getAllHashtag() {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(searchFacade.getHashtags())
        );
    }

    @GetMapping("/options/destinations")
    public ResponseEntity<ResponseDTO<ContinentNationResponse>> getAllContinentAndNationInfo() {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(searchFacade.getAllContinentAndPopularNationInfo())
        );
    }

    @GetMapping("/hashtags")
    public ResponseEntity<ResponseDTO<HashTagNamesResponse>> getAllHashtagNameBySearchedCountDesc() {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(searchFacade.findAllByOrderBySearchedCountDesc())
        );
    }
}
