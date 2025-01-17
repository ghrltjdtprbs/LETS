package com.yanolja_final.domain.packages.controller;

import com.yanolja_final.domain.packages.dto.response.PackageAvailableDateResponse;
import com.yanolja_final.domain.packages.dto.response.PackageCompareResponse;
import com.yanolja_final.domain.packages.dto.response.PackageDetailResponse;
import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.dto.response.PackageScheduleResponse;
import com.yanolja_final.domain.packages.facade.PackageFacade;
import com.yanolja_final.global.config.argumentresolver.LoginedUserId;
import com.yanolja_final.global.util.PagedResponseDTO;
import com.yanolja_final.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public PagedResponseDTO<PackageListItemResponse> listOfAll(
        @LoginedUserId Long userId,
        Pageable pageable
    ) {
        Page<PackageListItemResponse> list = packageFacade.getAllList(pageable, userId);
        return PagedResponseDTO.okWithData(list);
    }

    @GetMapping("/{id}/schedules")
    public ResponseDTO<List<PackageScheduleResponse>> schedules(
        @PathVariable Long id
    ) {
        List<PackageScheduleResponse> schedules = packageFacade.getSchedules(id);
        return ResponseDTO.okWithData(schedules);
    }

    @GetMapping("/{id}/available-dates")
    public ResponseDTO<List<PackageAvailableDateResponse>> availableDates(
        @PathVariable Long id
    ) {
        List<PackageAvailableDateResponse> schedules = packageFacade.getAvailableDates(id);
        return ResponseDTO.okWithData(schedules);
    }

    @GetMapping("/top-views")
    public PagedResponseDTO<PackageListItemResponse> topViews(
        @LoginedUserId Long userId,
        @RequestParam(required = false, defaultValue = "전체") String continentName,
        @RequestParam(required = false, defaultValue = "전체") String nationName,
        Pageable pageable
    ) {
        Page<PackageListItemResponse> list
            = packageFacade.getTopViews(pageable, userId, continentName, nationName);
        return PagedResponseDTO.okWithData(list);
    }

    @GetMapping("/top-purchases")
    public PagedResponseDTO<PackageListItemResponse> topPurchases(
        @LoginedUserId Long userId,
        Pageable pageable
    ) {
        Page<PackageListItemResponse> list = packageFacade.getTopPurchases(pageable, userId);
        return PagedResponseDTO.okWithData(list);
    }

    @GetMapping("/compare")
    public ResponseDTO<PackageCompareResponse> compare(
        @RequestParam Long fixedPackageId,
        @RequestParam Long comparePackageId
    ) {
        PackageCompareResponse compared = packageFacade.compare(fixedPackageId, comparePackageId);
        return ResponseDTO.okWithData(compared);
    }

    @GetMapping("/similar-packages")
    public PagedResponseDTO<PackageListItemResponse> similarPackages(
        @RequestParam Long fixedPackageId,
        @LoginedUserId Long userId,
        Pageable pageable
    ) {
        Page<PackageListItemResponse> list = packageFacade.getSimilarPackages(pageable,
            fixedPackageId, userId);
        return PagedResponseDTO.okWithData(list);
    }
}
