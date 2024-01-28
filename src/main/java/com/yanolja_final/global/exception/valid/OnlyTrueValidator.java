package com.yanolja_final.global.exception.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnlyTrueValidator implements ConstraintValidator<OnlyTrueCheck, Boolean> {

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        return value;
    }
}
