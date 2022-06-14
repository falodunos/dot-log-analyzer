package com.dot.file.reader.persistence.model.enums.constraints;

import com.dot.file.reader.persistence.model.enums.request.Duration;
import com.dot.file.reader.persistence.model.enums.request.duration.DurationSubsetValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DurationSubsetValidator.class)
public @interface DurationSubset {
    /**
     * @return subset of RequestType enum
     */
    Duration[] anyOf();

    /**
     * @return the error message template
     */
    String message() default "must be any of {anyOf}";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};
}