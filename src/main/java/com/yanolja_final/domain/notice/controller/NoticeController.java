package com.yanolja_final.domain.notice.controller;


import com.yanolja_final.domain.notice.dto.request.RegisterNoticeRequest;
import com.yanolja_final.domain.notice.dto.response.NoticeListResponse;
import com.yanolja_final.domain.notice.dto.response.NoticeResponse;
import com.yanolja_final.domain.notice.facade.NoticeFacade;
import com.yanolja_final.global.util.ResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeFacade noticeFacade;

    @PostMapping
    public ResponseEntity<ResponseDTO<NoticeResponse>> createNotice(
        @Valid @RequestBody RegisterNoticeRequest request
    ) {
        NoticeResponse response = noticeFacade.registerNotice(request);
        return ResponseEntity.ok(ResponseDTO.okWithData(response));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<NoticeListResponse>>> getNoticeList() {
        List<NoticeListResponse> response = noticeFacade.getNoticeList();
        return ResponseEntity.ok(ResponseDTO.okWithData(response));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<ResponseDTO<NoticeResponse>> getSpecificNotice(
        @PathVariable Long noticeId
    ) {
        NoticeResponse response = noticeFacade.getSpecificNotice(noticeId);
        return ResponseEntity.ok(ResponseDTO.okWithData(response));
    }
}
