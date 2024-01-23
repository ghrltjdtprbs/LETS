package com.yanolja_final.domain.packages.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class PassedDepartureDateException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.PASSED_DEPARTURE_DATE;

    public PassedDepartureDateException() { super(ERROR_CODE); }

}
