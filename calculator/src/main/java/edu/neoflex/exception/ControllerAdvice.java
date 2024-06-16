package edu.neoflex.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppScoringException.class)
    public ResponseEntity<?> handleAppException(AppScoringException exception) {
        String message = exception.getMessage();
        log.error(message);
        return ResponseEntity.badRequest().body(new ErrorDto("Reject", message));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppPrescoringException.class)
    public ResponseEntity<?> handleAppException(AppPrescoringException exception) {
        String message = exception.getMessage();
        log.error(message);
        return ResponseEntity.badRequest().body(new ErrorDto("Reject", message));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ", "", ""));
        log.error(message);
        return ResponseEntity.badRequest().body(new ErrorDto("Reject", message));
    }

    public record ErrorDto(String header, String message){}

}