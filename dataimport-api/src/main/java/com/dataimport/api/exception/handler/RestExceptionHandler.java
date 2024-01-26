package com.dataimport.api.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;


@ControllerAdvice
public class RestExceptionHandler implements ProblemHandling {

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Problem> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                         NativeWebRequest request) {

        ThrowableProblem problem = createThrowableProblem("Integrity violation error",
                Status.BAD_REQUEST, ex.getMessage());
        return handleProblem(problem, request);

    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Problem> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                  NativeWebRequest request) {

        ThrowableProblem problem = createThrowableProblem("Invalid argument",
                Status.BAD_REQUEST, ex.getMessage());
        return handleProblem(problem, request);

    }

    private ThrowableProblem createThrowableProblem(String title, Status status, String detail) {
        return Problem.builder().withTitle(title).withDetail(detail).withStatus(status).build();
    }

}