package com.yanolja_final.domain.packages.dto.response;

public record PackageCompareResponse(
    PackageSummaryResponse fixedPackage,
    PackageSummaryResponse comparePackage
) {

}
