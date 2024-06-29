package edu.neoflex.utils.impl;

import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.exception.AppPrescoringException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PrescoringProcessorImplTest {

    @InjectMocks
    PrescoringProcessorImpl prescoringProcessor;

    LoanStatementRequestDto loanStatementRequestDto;

    @Test
    void prescoring_whenInvalid_thenThrowAppPrescoringEx() {
        loanStatementRequestDto = LoanStatementRequestDto.builder()
                .birthdate(LocalDate.of(2100, 12, 12))
                .term(6)
                .email("test@mail.ru")
                .amount(BigDecimal.valueOf(30000))
                .passportSeries("0000")
                .passportNumber("000000")
                .lastName("Daniel")
                .firstName("Rudkovsky")
                .middleName("")
                .build();
        assertThrows(AppPrescoringException.class, () -> prescoringProcessor.prescoring(loanStatementRequestDto));
    }

    @Test
    void prescoring_whenValid_thenNotThrow() {
        loanStatementRequestDto = LoanStatementRequestDto.builder()
                .birthdate(LocalDate.of(2002, 12, 12))
                .build();
        assertDoesNotThrow(() -> prescoringProcessor.prescoring(loanStatementRequestDto));
    }
}