package com.yanolja_final.domain.search.controller;

import com.yanolja_final.domain.search.controller.response.ContinentNationResponse;
import com.yanolja_final.domain.search.controller.response.HashtagResponse;
import com.yanolja_final.domain.search.facade.SearchFacade;
import com.yanolja_final.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {

    private final SearchFacade searchFacade;

    @GetMapping("/options/hashtags")
    public ResponseEntity<ResponseDTO<List<HashtagResponse>>> getAllHashtag() {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(searchFacade.getHashtags())
        );
    }

    @GetMapping("/options/destinations")
    public ResponseEntity<ResponseDTO<List<ContinentNationResponse>>> getAllContinentAndNationInfo() {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(searchFacade.getAllContinentAndNationInfo())
        );
    }
}
