package com.example.validation.validators;

import com.example.validation.Enum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumStatusValidator implements ConstraintValidator<Enum, String> {
    private Class<? extends java.lang.Enum<?>> enumClass;

    @Override
    public void initialize(Enum annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));

        if (!isValid) {
            String possibleValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(java.lang.Enum::name)
                    .collect(Collectors.joining(", "));

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                String.format("invalid value '%s'. Should be one of: [%s]", value, possibleValues)
            ).addConstraintViolation();
        }
        return isValid;
    }
}
