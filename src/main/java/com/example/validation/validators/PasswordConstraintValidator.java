package com.example.validation.validators;

import com.example.validation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
    private static final String PASSWORD_PATTERN = "[A-Za-z0-9 ]*";
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 25;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasSpecial = !password.matches(PASSWORD_PATTERN);
        boolean isLongEnough = password.length() >= PASSWORD_MIN_LENGTH
                && password.length() <= PASSWORD_MAX_LENGTH;

        if (hasUppercase && hasSpecial && isLongEnough) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("""
                Password must be at least 8 characters long, include an uppercase letter,
                and a special character""")
                    .addConstraintViolation();
            return false;
        }
    }
}
