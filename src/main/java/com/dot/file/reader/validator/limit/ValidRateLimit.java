package com.dot.file.reader.validator.limit;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RateLimitValidator.class)
public @interface ValidRateLimit {
    String message() default "Invalid rate limit, please use 200 for hourly and 500 for daily";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}