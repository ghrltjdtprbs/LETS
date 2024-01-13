package com.yanolja_final.domain.notice.service;

import com.yanolja_final.domain.notice.dto.request.RegisterNoticeRequest;
import com.yanolja_final.domain.notice.dto.response.NoticeListResponse;
import com.yanolja_final.domain.notice.dto.response.NoticeResponse;
import com.yanolja_final.domain.notice.entity.Notice;
import com.yanolja_final.domain.notice.exception.NoticeNotFoundException;
import com.yanolja_final.domain.notice.repository.NoticeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeResponse registerNotice(RegisterNoticeRequest request) {
        Notice notice = request.toEntity();
        Notice newNotice = noticeRepository.save(notice);
        NoticeResponse response = NoticeResponse.from(newNotice);
        return response;
    }

    public List<NoticeListResponse> getNoticeList() {
        List<Notice> notices = noticeRepository.findAll();
        List<NoticeListResponse> response = NoticeListResponse.fromNotices(notices);
        return response;
    }

    public NoticeResponse getSpecificNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
            .orElseThrow(() -> new NoticeNotFoundException());
        NoticeResponse response = NoticeResponse.from(notice);
        return response;
    }
}
