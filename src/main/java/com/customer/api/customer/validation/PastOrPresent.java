package com.customer.api.customer.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PastOrPresentValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PastOrPresent {
    String message() default "The date must be in the past or present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

