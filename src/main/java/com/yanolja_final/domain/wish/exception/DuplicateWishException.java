package com.yanolja_final.domain.wish.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class DuplicateWishException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.WISH_ALREADY_EXISTS;

    public DuplicateWishException() {
        super(ERROR_CODE);
    }
}

