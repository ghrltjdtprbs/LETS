package com.yanolja_final.domain.order.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class InvalidCancelFeeAgreementException extends ApplicationException {

    public InvalidCancelFeeAgreementException() {
        super(ErrorCode.INVALID_CANCEL_FEE_AGREEMENT);
    }
}
