package com.yanolja_final.domain.packages.controller;

import com.yanolja_final.domain.packages.dto.response.PackageDetailResponse;
import com.yanolja_final.domain.packages.facade.PackageFacade;
import com.yanolja_final.global.config.argumentresolver.LoginedUserId;
import com.yanolja_final.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/packages")
public class PackageController {

    private final PackageFacade packageFacade;

    @GetMapping("/{id}")
    public ResponseDTO<PackageDetailResponse> detail(
        @PathVariable Long id,
        @LoginedUserId Long userId,
        @RequestParam(value = "departDate", required = false) String departDate
    ) {
        PackageDetailResponse detail = packageFacade.getDetail(id, userId, departDate);
        return ResponseDTO.okWithData(detail);
    }
}
