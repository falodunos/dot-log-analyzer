package com.dot.file.reader.validator.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface ValidDate {
    String message() default "Invalid date format, please use this date format: 'yyyy-MM-dd HH:mm:ss.SSS'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}