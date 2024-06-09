package edu.neoflex.utils.impl;

import edu.neoflex.dto.EmploymentDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.dto.enums.EmploymentPosition;
import edu.neoflex.dto.enums.EmploymentStatus;
import edu.neoflex.dto.enums.Gender;
import edu.neoflex.dto.enums.MaritalStatus;
import edu.neoflex.exception.AppPrescoringException;
import edu.neoflex.exception.AppScoringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class ScoringProcessorImplTest {

    @InjectMocks
    ScoringProcessorImpl scoringProcessor;

    ScoringDataDto scoringDataDto;

    LoanStatementRequestDto loanStatementRequestDto;

    @BeforeEach
    void init(){
        ReflectionTestUtils.setField(scoringProcessor, "loanBaseRate", BigDecimal.valueOf(10));
    }

    @Test
    void prescoring_whenInvalid_thenThrowAppPrescoringEx() {
        loanStatementRequestDto = LoanStatementRequestDto.builder()
                .birthdate(LocalDate.of(2100, 12, 12))
                .build();
        assertThrows(AppPrescoringException.class, () -> scoringProcessor.prescoring(loanStatementRequestDto));
    }

    @Test
    void prescoring_whenValid_thenNotThrow() {
        loanStatementRequestDto = LoanStatementRequestDto.builder()
                .birthdate(LocalDate.of(2002, 12, 12))
                .build();
        assertDoesNotThrow(() -> scoringProcessor.prescoring(loanStatementRequestDto));
    }

    @Test
    void scoring_whenNull_thenThrowNPE() {
        scoringDataDto  = ScoringDataDto.builder()
                .build();
        assertThrows(NullPointerException.class, () -> scoringProcessor.scoring(scoringDataDto));
    }

    @Test
    void scoring_whenInvalid_thenThrowAppScoringEx() {
        scoringDataDto  = ScoringDataDto.builder()
                .maritalStatus(MaritalStatus.MARRIED)
                .firstName("Daniel")
                .lastName("Rudkovsky")
                .amount(BigDecimal.valueOf(100000))
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .position(EmploymentPosition.MIDDLE_LEVEL_MANAGER)
                        .salary(BigDecimal.valueOf(20000))
                        .workExperienceTotal(30)
                        .workExperienceCurrent(10)
                        .build()
                )
                .term(6)
                .accountNumber("123")
                .gender(Gender.MALE)
                .birthdate(LocalDate.of(1800, 12, 12))
                .build();
        assertThrows(AppScoringException.class, () -> scoringProcessor.scoring(scoringDataDto));
    }

    @Test
    void scoring_whenValid_thenReturn() {
        scoringDataDto  = ScoringDataDto.builder()
                .maritalStatus(MaritalStatus.MARRIED)
                .firstName("Daniel")
                .lastName("Rudkovsky")
                .amount(BigDecimal.valueOf(100000))
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .position(EmploymentPosition.MIDDLE_LEVEL_MANAGER)
                        .salary(BigDecimal.valueOf(20000))
                        .workExperienceTotal(30)
                        .workExperienceCurrent(10)
                        .employerINN("1111111111")
                        .build()
                )
                .term(6)
                .accountNumber("123")
                .gender(Gender.MALE)
                .birthdate(LocalDate.of(1982, 12, 12))
                .build();
        assertEquals(BigDecimal.valueOf(3), scoringProcessor.scoring(scoringDataDto));

        scoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.BUSINESSMAN);
        scoringDataDto.getEmployment().setPosition(EmploymentPosition.TOP_LEVEL_MANAGER);
        scoringDataDto.setGender(Gender.FEMALE);
        scoringDataDto.setMaritalStatus(MaritalStatus.DIVORCED);
        assertEquals(BigDecimal.valueOf(7), scoringProcessor.scoring(scoringDataDto));

        scoringDataDto.setGender(Gender.NON_BINARY);
        assertEquals(BigDecimal.valueOf(17), scoringProcessor.scoring(scoringDataDto));
    }


}