package edu.neoflex.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ControllerAdviceTest {

    @InjectMocks
    ControllerAdvice controllerAdvice;

    @Test
    void handleAppException() {
        assertEquals(controllerAdvice.handleAppException(new AppPrescoringException(new Throwable("test"))).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}