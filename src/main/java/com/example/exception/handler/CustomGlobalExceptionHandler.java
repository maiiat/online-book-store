package com.example.exception.handler;

import com.example.exception.CategoryAlreadyExistException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.InvalidLoginException;
import com.example.exception.IsbnAlreadyExistException;
import com.example.exception.RegistrationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static void handleUnauthorizedServletLevelException(
            HttpServletResponse response,
            RuntimeException e,
            HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String errorJson = String.format("{\"timestamp\": \"%s\", "
                + "\"status\": \"%s\", \"errors\": [\"%s\"], \"path\": \"%s\"}",
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED,
                e.getMessage(),
                request.getRequestURI());
        response.getWriter().write(errorJson);
        response.getWriter().flush();
    }

    @ExceptionHandler({
            IsbnAlreadyExistException.class,
            RegistrationException.class,
            EntityNotFoundException.class,
            CategoryAlreadyExistException.class})
    public ResponseEntity<Object> handleCustomException(
            RuntimeException ex,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", Stream.of(ex.getMessage()));
        body.put("path", getPath(request));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<Object> handleInvalidLoginException(
            RuntimeException ex,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED);
        body.put("errors", Stream.of(ex.getMessage()));
        body.put("path", getPath(request));
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .sorted()
                .toList();
        body.put("errors", errors);
        body.put("path", getPath(request));
        return new ResponseEntity<>(body, headers, status);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError fieldError) {
            String field = fieldError.getField();
            String message = e.getDefaultMessage();
            return "field:'" + field + "' " + message;
        }
        return e.getDefaultMessage();
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
