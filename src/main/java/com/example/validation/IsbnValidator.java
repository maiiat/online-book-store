package com.example.validation;

import com.example.service.BookService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {

    private static final Pattern pattern = Pattern.compile(
            "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})"
                    + "[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"
    );

    @Autowired
    private BookService bookService;

    @Override
    public void initialize(Isbn constraintAnnotation) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null) {
            return true;
        }

        if (!pattern.matcher(isbn).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("invalid ISBN format")
                    .addConstraintViolation();
            return false;
        }

        if (bookService.isIsbnExists(isbn)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("such ISBN already exist")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
