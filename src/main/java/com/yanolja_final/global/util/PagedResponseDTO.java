package com.yanolja_final.global.util;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Getter
public class PagedResponseDTO<T> {

    private final int code;
    private final List<T> data;
    private final PageInfo page;
    private final String message;

    @Builder
    private PagedResponseDTO(int code, String message, PageInfo page, List<T> data) {
        this.code = code;
        this.message = message;
        this.page = page;
        this.data = data;
    }

    public static <T> PagedResponseDTO<T> okWithData(Page<T> data) {
        return PagedResponseDTO.<T>builder()
            .code(HttpStatus.OK.value())
            .data(data.getContent())
            .page(PageInfo.from(data))
            .message(null)
            .build();
    }

    public static <T> PagedResponseDTO<T> okWithMessageAndData(String message, Page<T> data) {
        return PagedResponseDTO.<T>builder()
            .code(HttpStatus.OK.value())
            .data(data.getContent())
            .page(PageInfo.from(data))
            .message(message)
            .build();
    }
}
