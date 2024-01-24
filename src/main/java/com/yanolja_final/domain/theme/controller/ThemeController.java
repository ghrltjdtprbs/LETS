package com.yanolja_final.domain.theme.controller;

import com.yanolja_final.domain.theme.dto.response.ThemePackageResponse;
import com.yanolja_final.domain.theme.dto.response.ThemeResponse;
import com.yanolja_final.domain.theme.facade.ThemeFacade;
import com.yanolja_final.global.util.PagedResponseDTO;
import com.yanolja_final.global.util.ResponseDTO;

import java.util.List;

import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeFacade themeFacade;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ThemeResponse>>> getAllThemes() {
        List<ThemeResponse> themes = themeFacade.getAllThemes();
        return ResponseEntity.ok(ResponseDTO.okWithData(themes));
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<PagedResponseDTO<ThemePackageResponse>> getThemePackages(
        @PathVariable Long themeId,
        @RequestParam(defaultValue = "departure_date") String sortBy,
        @PageableDefault Pageable pageable) {

        Page<ThemePackageResponse> themePackagesPage = themeFacade.getThemePackages(themeId, sortBy, pageable);
        PagedResponseDTO<ThemePackageResponse> response = PagedResponseDTO.okWithData(
            themePackagesPage);

        return ResponseEntity.ok(response);
    }
}
