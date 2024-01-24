package com.yanolja_final.global.util;

import org.springframework.data.domain.Page;

public record PageInfo(
    int currentPage,
    int totalPages,
    int currentElements,
    long totalElements
) {

    public static <T> PageInfo from(Page<T> page) {
        return new PageInfo(
            page.getNumber() + 1,
            page.getTotalPages(),
            page.getNumberOfElements(),
            page.getTotalElements()
        );
    }
}
