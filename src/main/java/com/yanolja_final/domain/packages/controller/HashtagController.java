package com.yanolja_final.domain.packages.controller;

import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.facade.HashtagFacade;
import com.yanolja_final.global.config.argumentresolver.LoginedUserId;
import com.yanolja_final.global.util.PagedResponseDTO;
import com.yanolja_final.global.util.ResponseDTO;
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
@RequestMapping("/v1/hashtag-search")
public class HashtagController {

    private final HashtagFacade hashtagFacade;

    @GetMapping
    public ResponseEntity<PagedResponseDTO<PackageListItemResponse>> getPackagesByHashtagKeyword(
        @LoginedUserId Long userId,
        @RequestParam String keyword,
        Pageable pageable,
        @RequestParam(defaultValue = "departure_date", required = false) String sortBy
    ) {
        return ResponseEntity.ok(
            PagedResponseDTO.okWithData(hashtagFacade.getPackagesByHashtagKeyword(userId, keyword, pageable, sortBy))
        );
    }
}
