package com.example.validation.validators;

import com.example.validation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
    private static final String PASSWORD_PATTERN = "[A-Za-z0-9 ]*";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasSpecial = !password.matches(PASSWORD_PATTERN);
        return hasUppercase && hasSpecial;
    }
}
