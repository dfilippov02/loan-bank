package edu.neoflex.service.impl;

import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.EmploymentDto;
import edu.neoflex.dto.PaymentScheduleElementDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.dto.enums.EmploymentPosition;
import edu.neoflex.dto.enums.EmploymentStatus;
import edu.neoflex.utils.LoanParamsCalculator;
import edu.neoflex.utils.ScoringProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    ScoringProcessor scoringProcessor;

    @Mock
    LoanParamsCalculator loanParamsCalculator;

    @InjectMocks
    LoanServiceImpl loanService;

    ScoringDataDto scoringDataDto;
    BigDecimal rate;
    CreditDto expectedCreditDto;

    @BeforeEach
    void init() {

        rate = BigDecimal.valueOf(7);

        scoringDataDto = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(6)
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .position(EmploymentPosition.TOP_LEVEL_MANAGER)
                        .salary(BigDecimal.valueOf(30000))
                        .workExperienceCurrent(10)
                        .workExperienceTotal(30)
                        .build()
                )
                .build();

        List<PaymentScheduleElementDto> paymentScheduleElementDtoList = new ArrayList<>();

        LocalDate date = LocalDate.now();

        paymentScheduleElementDtoList.add(PaymentScheduleElementDto.builder()
                .number(1)
                .date(date)
                .totalPayment(BigDecimal.valueOf(17008.59))
                .debtPayment(BigDecimal.valueOf(16425.26))
                .interestPayment(BigDecimal.valueOf(583.33))
                .remainingDebt(BigDecimal.valueOf(83574.74))
                .build()
        );
        paymentScheduleElementDtoList.add(PaymentScheduleElementDto.builder()
                .number(2)
                .date(date.plusMonths(1))
                .totalPayment(BigDecimal.valueOf(17008.59))
                .debtPayment(BigDecimal.valueOf(16521.07))
                .interestPayment(BigDecimal.valueOf(487.52))
                .remainingDebt(BigDecimal.valueOf(67053.67))
                .build()
        );
        paymentScheduleElementDtoList.add(PaymentScheduleElementDto.builder()
                .number(3)
                .date(date.plusMonths(2))
                .totalPayment(BigDecimal.valueOf(17008.59))
                .debtPayment(BigDecimal.valueOf(16617.44))
                .interestPayment(BigDecimal.valueOf(391.15))
                .remainingDebt(BigDecimal.valueOf(50436.23))
                .build()
        );
        paymentScheduleElementDtoList.add(PaymentScheduleElementDto.builder()
                .number(4)
                .date(date.plusMonths(3))
                .totalPayment(BigDecimal.valueOf(17008.59))
                .debtPayment(BigDecimal.valueOf(16714.38))
                .interestPayment(BigDecimal.valueOf(294.21))
                .remainingDebt(BigDecimal.valueOf(33721.85))
                .build()
        );
        paymentScheduleElementDtoList.add(PaymentScheduleElementDto.builder()
                .number(5)
                .date(date.plusMonths(4))
                .totalPayment(BigDecimal.valueOf(17008.59))
                .debtPayment(BigDecimal.valueOf(16811.88))
                .interestPayment(BigDecimal.valueOf(196.71))
                .remainingDebt(BigDecimal.valueOf(16909.97))
                .build()
        );
        paymentScheduleElementDtoList.add(PaymentScheduleElementDto.builder()
                .number(6)
                .date(date.plusMonths(5))
                .totalPayment(BigDecimal.valueOf(17008.59))
                .debtPayment(BigDecimal.valueOf(16909.95))
                .interestPayment(BigDecimal.valueOf(98.64))
                .remainingDebt(BigDecimal.valueOf(0.02))
                .build()
        );

        expectedCreditDto = CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .rate(rate)
                .monthlyPayment(BigDecimal.valueOf(17008.59))
                .psk(BigDecimal.valueOf(102051.54))
                .paymentSchedule(paymentScheduleElementDtoList)
                .build();

    }

    @Test
    void calculateLoan() {

        Mockito.when(scoringProcessor.scoring(any())).thenReturn(rate);
        Mockito.when(loanParamsCalculator.countMonthPayment(any(), any(), anyInt())).thenReturn(BigDecimal.valueOf(17008.59));
        Mockito.when(loanParamsCalculator.countTotalAmount(any(), anyInt())).thenReturn(BigDecimal.valueOf(102051.54));
        Mockito.when(loanParamsCalculator.countPercentsAmount(any(), any()))
                .thenReturn(BigDecimal.valueOf(583.33))
                .thenReturn(BigDecimal.valueOf(487.52))
                .thenReturn(BigDecimal.valueOf(391.15))
                .thenReturn(BigDecimal.valueOf(294.21))
                .thenReturn(BigDecimal.valueOf(196.71))
                .thenReturn(BigDecimal.valueOf(98.64));

        CreditDto creditDto = loanService.calculateLoan(scoringDataDto);
        assertEquals(creditDto, expectedCreditDto);
    }

    @Test
    void calculateLoan_whenNullThenThrowNPE() {
        assertThrows(NullPointerException.class, () -> loanService.calculateLoan(null));

    }
}