package com.course.exception;

import com.course.controller.AuthController;
import com.course.dto.ErrorResponse;
import com.course.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void businessRuleKeepsTheStatusAndMessage() {
        ResponseEntity<ErrorResponse> response = handler.businessRule(
                new BusinessRuleException(HttpStatus.NOT_FOUND, "Module not found"));

        assertEquals(404, response.getStatusCode().value());
        assertEquals("Module not found", response.getBody().message());
        assertNull(response.getBody().openLessonId());
    }

    @Test
    void lockedLessonPointsAtTheOpenLesson() {
        ResponseEntity<ErrorResponse> response = handler.locked(new LessonLockedException(4L));

        assertEquals(403, response.getStatusCode().value());
        assertEquals("Finish the previous lesson before opening this one", response.getBody().message());
        assertEquals(4L, response.getBody().openLessonId());
    }

    @Test
    void validationSkipsAMissingMessage() throws Exception {
        BeanPropertyBindingResult binding = new BeanPropertyBindingResult(new LoginRequest("", ""), "request");
        binding.addError(new FieldError("request", "username", null, false, null, null, null));
        binding.addError(new FieldError("request", "password", "Password is required"));
        Method method = AuthController.class.getMethod("login", LoginRequest.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(new MethodParameter(method, 0), binding);

        ResponseEntity<ErrorResponse> response = handler.validation(exception);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Password is required", response.getBody().message());
    }

    @Test
    void unreadableBodyDoesNotEchoThePayload() {
        ResponseEntity<ErrorResponse> response = handler.unreadable(
                new org.springframework.http.converter.HttpMessageNotReadableException("password=senha1234"));

        assertEquals(400, response.getStatusCode().value());
        assertEquals("The request body is invalid", response.getBody().message());
    }

    @Test
    void unexpectedFailureHidesTheCause() {
        ResponseEntity<ErrorResponse> response = handler.unexpected(new IllegalStateException("database password"));

        assertEquals(500, response.getStatusCode().value());
        assertEquals("The request could not be completed", response.getBody().message());
    }

    @Test
    void typeMismatchRejectsABadAddress() {
        ResponseEntity<ErrorResponse> response = handler.typeMismatch(
                new MethodArgumentTypeMismatchException("abc", Long.class, "id", null, new IllegalArgumentException("abc")));

        assertEquals(400, response.getStatusCode().value());
        assertEquals("The request address is invalid", response.getBody().message());
    }

    @Test
    void missingResourceIsNotFound() {
        ResponseEntity<ErrorResponse> response = handler.missing(new NoResourceFoundException(HttpMethod.GET, "/api/unknown"));

        assertEquals(404, response.getStatusCode().value());
        assertEquals("The requested resource was not found", response.getBody().message());
    }
}
