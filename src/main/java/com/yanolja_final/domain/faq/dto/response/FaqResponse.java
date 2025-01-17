package com.yanolja_final.domain.faq.dto.response;

import com.yanolja_final.domain.faq.entity.Faq;
import java.util.Arrays;

public record FaqResponse(
    Long faqId,
    String title,
    String createdAt,
    String[] categories,
    String[] content
) {
    public static FaqResponse from(Faq faq) {
        String[] splitCategories = faq.getCategories().split(",");
        splitCategories = Arrays.stream(splitCategories)
            .map(String::trim)
            .toArray(String[]::new);

        String[] splitContent = faq.getContent().split("\n");
        splitContent = Arrays.stream(splitContent)
            .map(String::trim)
            .toArray(String[]::new);

        return new FaqResponse(
            faq.getId(),
            faq.getTitle(),
            faq.getFormattedDate(),
            splitCategories,
            splitContent
        );
    }
}
