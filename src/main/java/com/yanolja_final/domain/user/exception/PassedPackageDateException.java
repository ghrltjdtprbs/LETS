package com.yanolja_final.domain.user.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class PassedPackageDateException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.PASSED_PACKAGE_DATE;

    public PassedPackageDateException() { super(ERROR_CODE); }

}
