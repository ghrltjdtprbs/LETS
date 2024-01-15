package com.yanolja_final.domain.advertisement.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class AdvertisementNotFound extends ApplicationException {

    public AdvertisementNotFound() {
        super(ErrorCode.ADVERTISEMENT_NOT_FOUND);
    }
}
