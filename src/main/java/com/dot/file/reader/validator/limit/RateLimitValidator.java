package com.dot.file.reader.validator.limit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RateLimitValidator implements ConstraintValidator<ValidRateLimit, Integer> {
    @Override
    public void initialize(ValidRateLimit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer rateLimit, ConstraintValidatorContext constraintValidatorContext) {
        return rateLimit != null && String.valueOf(rateLimit).matches("[0-9]+")
                && (rateLimit == 200 || rateLimit == 500);
    }
}
