package com.example.validation.validators;

import com.example.service.BookService;
import com.example.validation.Isbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {

    private static final Pattern ISBN_PATTERN = Pattern.compile(
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
        return isbn != null && ISBN_PATTERN
            .matcher(isbn)
            .matches();
    }
}
