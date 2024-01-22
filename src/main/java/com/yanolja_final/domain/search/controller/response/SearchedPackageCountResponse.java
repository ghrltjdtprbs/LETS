package com.yanolja_final.domain.search.controller.response;

public record SearchedPackageCountResponse(
    Integer count
) {

    public static SearchedPackageCountResponse from(Integer count) {
        return new SearchedPackageCountResponse(
            count
        );
    }
}
