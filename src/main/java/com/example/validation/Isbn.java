package com.example.validation;

import com.example.validation.validators.IsbnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Isbn {
    String message() default "invalid ISBN";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
