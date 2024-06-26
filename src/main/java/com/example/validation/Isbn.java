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
    String message() default "invalid ISBN format. example = \"978-3-16-148410-0\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
