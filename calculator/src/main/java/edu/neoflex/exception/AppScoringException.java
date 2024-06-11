package edu.neoflex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AppScoringException extends RuntimeException{


    public AppScoringException(Throwable throwable) {
        super(throwable);
    }
}
