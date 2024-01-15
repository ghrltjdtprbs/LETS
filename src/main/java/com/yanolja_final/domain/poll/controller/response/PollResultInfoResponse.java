package com.yanolja_final.domain.poll.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanolja_final.domain.poll.entity.Poll;

public record PollResultInfoResponse(
    String linkName,
    String linkHashTag,
    Integer count,
    Integer percentage,
    @JsonProperty("selected")
    boolean isSelected
) {

    public static PollResultInfoResponse getAPollResultInfo(Poll poll, boolean isSelected) {
        return new PollResultInfoResponse(
            poll.getAName(),
            poll.getAHashtag(),
            poll.getACount(),
            calculatePercentage(poll.getACount(), poll.getACount() + poll.getBCount()),
            isSelected
        );
    }

    public static PollResultInfoResponse getBPollResultInfo(Poll poll, boolean isSelected) {
        return new PollResultInfoResponse(
            poll.getBName(),
            poll.getBHashtag(),
            poll.getBCount(),
            calculatePercentage(poll.getBCount(), poll.getACount() + poll.getBCount()),
            isSelected
        );
    }

    private static int calculatePercentage(int count, int totalCount) {
        if (totalCount == 0) {
            return 0;
        }
        return (int) Math.round(((double) count / totalCount) * 100.0);
    }
}
