package com.yanolja_final.domain.poll.controller.response;

import com.yanolja_final.domain.poll.entity.Poll;

public record PollResultResponse(
    boolean alreadySubmitted,
    String subject,
    PollResultInfoResponse A,
    PollResultInfoResponse B,
    Integer totalCount
) {

    public static PollResultResponse from(Poll poll, Character answer) {
        return new PollResultResponse(
            true,
            poll.getTitle(),
            PollResultInfoResponse.getAPollResultInfo(poll, answer.equals('A')),
            PollResultInfoResponse.getBPollResultInfo(poll, answer.equals('B')),
            poll.getACount() + poll.getBCount()
        );
    }
}
