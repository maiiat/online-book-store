package com.example.validation.validators;

import com.example.service.AuthenticationService;
import com.example.validation.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("invalid email format")
                    .addConstraintViolation();
            return false;
        }

        if (authenticationService.isUserEmailExists(email)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("email already exists")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
