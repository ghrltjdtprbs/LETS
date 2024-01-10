package com.yanolja_final.domain.order.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class MaximumCapacityExceededException extends ApplicationException {

    public MaximumCapacityExceededException() {
        super(ErrorCode.MAXIMUN_CAPACITY);
    }
}
