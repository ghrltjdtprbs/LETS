package com.yanolja_final.domain.theme.dto.response;

import java.util.List;

public record ThemePackageResponse(
    Long themeId,
    String name,
    String description,
    Integer minPrice,
    String imageUrl,
    List<PackageSummary> packages
) { }
