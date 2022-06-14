package com.dot.file.reader.validator.date;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {
    @Override
    public void initialize(ValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        log.info("... Calling isValid Method ...");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setLenient(false);
        boolean status = true;
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            log.info("Date '" +  dateStr +"' is not valid; please use date with format 'yyyy-MM-dd HH:mm:ss.SSS'");
            status = false;
        }
        return status;
    }
}
