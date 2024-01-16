package com.yanolja_final.domain.packages.controller;

import com.yanolja_final.domain.packages.dto.response.PackageAvailableDateResponse;
import com.yanolja_final.domain.packages.dto.response.PackageDetailResponse;
import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.dto.response.PackageScheduleResponse;
import com.yanolja_final.domain.packages.facade.PackageFacade;
import com.yanolja_final.global.config.argumentresolver.LoginedUserId;
import com.yanolja_final.global.util.PaginationUtils;
import com.yanolja_final.global.util.ResponseDTO;
import java.util.List;
import java.util.Map;
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
    public ResponseDTO<Map<String, Object>> listOfAll(
        @LoginedUserId Long userId,
        Pageable pageable
    ) {
        Page<PackageListItemResponse> list = packageFacade.getAllList(pageable, userId);
        return ResponseDTO.okWithData(PaginationUtils.createPageResponse(list));
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
    public ResponseDTO<Map<String, Object>> topViews(
        Pageable pageable
    ) {
        Page<PackageListItemResponse> list = packageFacade.getTopViews(pageable);
        return ResponseDTO.okWithData(PaginationUtils.createPageResponse(list));
    }
}
