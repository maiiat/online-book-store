package com.example.validation.validators;

import com.example.validation.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        field = constraintAnnotation.first();
        fieldMatch = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object field = new BeanWrapperImpl(value).getPropertyValue(this.field);
        Object fieldMatch = new BeanWrapperImpl(value).getPropertyValue(this.fieldMatch);
        return Objects.equals(field, fieldMatch);
    }
}
