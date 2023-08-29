package com.hopsncode.challenge.notifications.common.advice;

import com.hopsncode.challenge.notifications.common.exception.ApplicationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.Violation;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class DefaultAdvice implements ProblemHandling {

    @Override
    public ResponseEntity<Problem> handleConstraintViolation(ConstraintViolationException exception, NativeWebRequest req) {
        return create(Problem.builder()
                .withStatus(Status.BAD_REQUEST)
                .withTitle("Invalid information")
                .with("code", "GN-VAL-0001")
                .with("violations",
                        exception.getConstraintViolations().stream()
                                .map(violation -> {
                                    String propertyPath = violation.getPropertyPath().toString();
                                    return new Violation(propertyPath.substring(propertyPath.lastIndexOf('.') + 1), violation.getMessage());
                                })
                                .toList())
                .build(), req);
    }

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<Problem> handleApplicationException(ApplicationException exception, NativeWebRequest req) {
        return create(Problem.builder()
                .withStatus(Status.valueOf(exception.getHttpStatus().value()))
                .withTitle(Optional.ofNullable(exception.getTitle())
                        .orElse("Application error"))
                .withDetail(exception.getMessage())
                .with("exceptionName", exception.getClass().getSimpleName())
                .with("code", exception.getErrorCode())
                .build(), req);
    }
}
