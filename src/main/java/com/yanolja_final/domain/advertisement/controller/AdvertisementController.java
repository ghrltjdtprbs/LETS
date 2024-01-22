package com.yanolja_final.domain.advertisement.controller;

import com.yanolja_final.domain.advertisement.dto.response.AdDetailResponse;
import com.yanolja_final.domain.advertisement.dto.response.AdListItemResponse;
import com.yanolja_final.domain.advertisement.facade.AdFacade;
import com.yanolja_final.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/advertisements")
public class AdvertisementController {

    private final AdFacade adFacade;

    @GetMapping
    public ResponseDTO<List<AdListItemResponse>> getAdList() {
        List<AdListItemResponse> list = adFacade.getList();
        return ResponseDTO.okWithData(list);
    }

    @GetMapping("/{id}")
    public ResponseDTO<AdDetailResponse> getAdDetail(
        @PathVariable Long id
    ) {
        AdDetailResponse detail = adFacade.getDetail(id);
        return ResponseDTO.okWithData(detail);
    }
}
