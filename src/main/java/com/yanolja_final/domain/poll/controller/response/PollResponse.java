package com.yanolja_final.domain.poll.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanolja_final.domain.poll.entity.Poll;

public record PollResponse(
    @JsonProperty("alreadySubmitted")
    boolean isAlreadySubmitted,
    String subject,
    Long pollId,
    String[] A,
    String[] B
) {

    public static PollResponse from(Poll poll, boolean isAlreadySubmitted) {
        if (isAlreadySubmitted) {
            return new PollResponse(true, null, null, null, null);
        } else {
            return new PollResponse(
                false,
                poll.getTitle(),
                poll.getId(),
                slideString(poll.getAQuestion()),
                slideString(poll.getBQuestion())
            );
        }
    }


    private static String[] slideString(String pollQuestion) {
        return pollQuestion.split("\n");
    }
}
