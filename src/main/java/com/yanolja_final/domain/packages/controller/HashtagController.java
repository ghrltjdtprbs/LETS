package com.yanolja_final.domain.packages.controller;

import com.yanolja_final.domain.packages.repository.HashtagRepository;
import com.yanolja_final.global.util.ResponseDTO;
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

    private final HashtagRepository hashtagRepository;

    @GetMapping
    public ResponseEntity<ResponseDTO<Void>>  getPackagesByHashtagKeyword(
        @RequestParam String keyword,
        Pageable pageable,
        @RequestParam String sortBy
    ){

    }
}
