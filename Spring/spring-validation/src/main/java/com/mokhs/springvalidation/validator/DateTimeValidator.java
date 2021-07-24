package com.mokhs.springvalidation.validator;

import com.mokhs.springvalidation.annotation.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeValidator implements ConstraintValidator<DateTime, String> {


    private String pattern;

    @Override
    public void initialize(DateTime constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            LocalDate.parse(value, DateTimeFormatter.ofPattern(this.pattern));
        } catch (Exception e) {
            return false;
        }

        return true;

    }
}
