package com.yanolja_final.domain.user.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class DuplicatedCurrentPasswordException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DUPLICATED_CURRENT_PASSWORD;

    public DuplicatedCurrentPasswordException() { super(ERROR_CODE); }
}
