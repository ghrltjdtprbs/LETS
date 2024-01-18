package com.yanolja_final.domain.packages.exception;

import com.yanolja_final.global.exception.ApplicationException;
import com.yanolja_final.global.exception.ErrorCode;

public class PackageDateNotFoundException extends ApplicationException {

    public PackageDateNotFoundException() {
        super(ErrorCode.PACKAGE_DATE_NOT_FOUND);
    }
}
