package com.yanolja_final.domain.poll.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class PollNotVotedException extends ApplicationException {

    public PollNotVotedException() {
        super(ErrorCode.NOT_VOTED);
    }
}
