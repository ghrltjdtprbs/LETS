package com.yanolja_final.domain.notice.facade;


import com.yanolja_final.domain.notice.dto.request.RegisterNoticeRequest;
import com.yanolja_final.domain.notice.dto.response.NoticeListResponse;
import com.yanolja_final.domain.notice.dto.response.NoticeResponse;
import com.yanolja_final.domain.notice.service.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeFacade {

    private final NoticeService noticeService;

    public NoticeResponse registerNotice(RegisterNoticeRequest request) {
        NoticeResponse response = noticeService.registerNotice(request);
        return response;
    }

    public List<NoticeListResponse> getNoticeList() {
        List<NoticeListResponse> response = noticeService.getNoticeList();
        return response;
    }

    public NoticeResponse getSpecificNotice(Long noticeId) {
        NoticeResponse response = noticeService.getSpecificNotice(noticeId);
        return response;
    }
}
