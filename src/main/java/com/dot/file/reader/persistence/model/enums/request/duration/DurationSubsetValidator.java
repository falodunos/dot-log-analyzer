package com.dot.file.reader.persistence.model.enums.request.duration;

import com.dot.file.reader.persistence.model.enums.constraints.DurationSubset;
import com.dot.file.reader.persistence.model.enums.request.Duration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class DurationSubsetValidator implements ConstraintValidator<DurationSubset, Duration> {
    private Duration[] subset;

    /**
     * @param constraint
     */
    @Override
    public void initialize(DurationSubset constraint) {
        this.subset = constraint.anyOf();
    }


    /**
     * @param callMode
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Duration callMode, ConstraintValidatorContext context) {
        return callMode == null || Arrays.asList(subset)
                .contains(callMode);
    }
}