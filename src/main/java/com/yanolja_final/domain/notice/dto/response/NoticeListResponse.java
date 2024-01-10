package com.yanolja_final.domain.notice.dto.response;

import com.yanolja_final.domain.notice.entity.Notice;
import java.util.List;
import java.util.stream.Collectors;

public record NoticeListResponse(

    Long noticeId,
    String title,
    String createdAt,
    String[] categories

) {
    public static NoticeListResponse fromNotice(Notice notice) {
        String[] splitCategories = notice.getCategories().split(",");

        return new NoticeListResponse(
            notice.getId(),
            notice.getTitle(),
            notice.getFormattedDate(),
            splitCategories
        );
    }

    public static List<NoticeListResponse> fromNotices(List<Notice> notices) {
        return notices.stream()
            .map(NoticeListResponse::fromNotice)
            .collect(Collectors.toList());
    }
}
