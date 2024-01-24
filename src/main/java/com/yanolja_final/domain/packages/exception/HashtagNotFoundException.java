package com.yanolja_final.domain.packages.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class HashtagNotFoundException extends ApplicationException {


    public HashtagNotFoundException() {
        super(ErrorCode.HASHTAG_NOT_FOUND);
    }
}
