package com.yanolja_final.domain.poll.controller;

import com.yanolja_final.domain.poll.controller.request.PollAnswerRequest;
import com.yanolja_final.domain.poll.controller.response.PollResponse;
import com.yanolja_final.domain.poll.controller.response.PollResultResponse;
import com.yanolja_final.domain.poll.facade.PollFacade;
import com.yanolja_final.global.config.argumentresolver.LoginedUserId;
import com.yanolja_final.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/polls")
@RequiredArgsConstructor
public class PollController {

    private final PollFacade pollFacade;

    @GetMapping("/main")
    ResponseEntity<ResponseDTO<PollResponse>> getActivePollForMainPage() {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(pollFacade.getActivePollForMainPage())
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<PollResponse>> getActivePoll(
        @LoginedUserId Long userId
    ) {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(pollFacade.getActivePoll(userId))
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> submit(
        @LoginedUserId Long userId,
        @Valid @RequestBody PollAnswerRequest request
    ) {
        pollFacade.savePollAnswer(userId, request);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @GetMapping("/result")
    public ResponseEntity<ResponseDTO<PollResultResponse>> getActivePollResult(
        @LoginedUserId Long userId
    ) {
        return ResponseEntity.ok(
            ResponseDTO.okWithData(pollFacade.getActivePollResult(userId))
        );
    }
}
