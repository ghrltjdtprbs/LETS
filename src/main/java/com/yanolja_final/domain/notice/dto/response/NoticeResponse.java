package com.yanolja_final.domain.notice.dto.response;

import com.yanolja_final.domain.notice.entity.Notice;


public record NoticeResponse(
    Long noticeId,
    String title,
    String createdAt,
    String[] content,
    String[] categories
) {

    public static NoticeResponse fromNotice(Notice notice) {

        String[] splitContent = notice.getContent().split("\n");
        String[] splitCategories = notice.getCategories().split(",");

        return new NoticeResponse(
            notice.getId(),
            notice.getTitle(),
            notice.getFormattedDate(),
            splitContent,
            splitCategories
        );
    }
}

