package edu.neoflex.controller;

import edu.neoflex.exception.AppPrescoringException;
import edu.neoflex.exception.AppScoringException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ExceptionHandler(AppScoringException.class)
    public ResponseEntity<?> handleAppException(AppScoringException exception) {
        String message = exception.getMessage();
        log.error(message);
        return ResponseEntity.badRequest().body(Map.of("header", "Reject", "message", message));
    }

    @ExceptionHandler(AppPrescoringException.class)
    public ResponseEntity<?> handleAppException(AppPrescoringException exception) {
        String message = exception.getMessage();
        log.error(message);
        return ResponseEntity.badRequest().body(Map.of("header", "Reject", "message", message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ", "", ""));
        log.error(message);
        return ResponseEntity.badRequest().body(Map.of("header", "Reject" ,"message", message));
    }

}
