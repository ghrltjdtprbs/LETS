package com.yanolja_final.domain.user.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class AgreeNotTureException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.AGREE_IS_NOT_TRUE;

    public AgreeNotTureException() {
        super(ERROR_CODE);
    }
}
