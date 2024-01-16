package com.yanolja_final.domain.packages.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class AvailableDateNotFoundException extends ApplicationException {

    public AvailableDateNotFoundException() {
        super(ErrorCode.AVAILABLE_DATE_NOT_FOUND);
    }
}
