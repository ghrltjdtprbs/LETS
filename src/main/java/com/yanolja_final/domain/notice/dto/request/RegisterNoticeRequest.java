package com.yanolja_final.domain.notice.dto.request;

import com.yanolja_final.domain.notice.entity.Notice;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.print.DocFlavor.STRING;
import org.aspectj.weaver.ast.Not;

public record RegisterNoticeRequest(
    @NotNull
    String title,
    @NotNull
    String[] content,
    @NotNull
    String[] categories
) {
    public Notice toEntity() {
        String splitContent = Arrays.stream(content)
            .map(String::trim)
            .collect(Collectors.joining("\n"));
        String splitCategories = Arrays.stream(categories)
            .map(String::trim)
            .collect(Collectors.joining(","));

        return Notice.builder()
            .title(title)
            .content(splitContent)
            .categories(splitCategories)
            .build();
    }
}


