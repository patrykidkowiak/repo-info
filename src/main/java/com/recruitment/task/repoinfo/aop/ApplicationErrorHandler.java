package com.recruitment.task.repoinfo.aop;

import com.recruitment.task.repoinfo.exception.ParseFromStringToJsonNotPossibleException;
import com.recruitment.task.repoinfo.exception.UserOrRepositoryNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {
    @ExceptionHandler(UserOrRepositoryNotFoundException.class)
    public ResponseEntity handleUserOrRepositoryNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ParseFromStringToJsonNotPossibleException.class)
    public ResponseEntity handleParseFromStringToJsonNotPossibleException() {
        return ResponseEntity.notFound().build();
    }
}
