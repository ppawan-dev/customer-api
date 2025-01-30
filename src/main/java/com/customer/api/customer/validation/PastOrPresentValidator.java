package com.customer.api.customer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PastOrPresentValidator implements ConstraintValidator<PastOrPresent, LocalDate> {

    @Override
    public void initialize(PastOrPresent constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // null values are valid, use @NotNull for null checks
        }
        return !date.isAfter(LocalDate.now());
    }
}

