package com.yanolja_final.domain.order.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class NoGuestsException extends ApplicationException {

    public NoGuestsException() {
        super(ErrorCode.NO_GUESTS);
    }
}
