package edu.neoflex.utils.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LoanParamsCalculatorImplTest {

    @InjectMocks
    LoanParamsCalculatorImpl loanParamsCalculator;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(loanParamsCalculator, "loanBaseRate", BigDecimal.valueOf(10));
        ReflectionTestUtils.setField(loanParamsCalculator, "loanInsuranceCost", BigDecimal.valueOf(2));
        ReflectionTestUtils.setField(loanParamsCalculator, "loanInsuranceDiscount", BigDecimal.valueOf(2));
        ReflectionTestUtils.setField(loanParamsCalculator, "salaryClientDiscount", BigDecimal.valueOf(1));
    }

    @Test
    void countLoanRate() {
        assertEquals(loanParamsCalculator.countLoanRate(false, false), BigDecimal.valueOf(10));
        assertEquals(loanParamsCalculator.countLoanRate(true, false), BigDecimal.valueOf(9));
        assertEquals(loanParamsCalculator.countLoanRate(false, true), BigDecimal.valueOf(8));
        assertEquals(loanParamsCalculator.countLoanRate(true, true), BigDecimal.valueOf(7));
    }

    @Test
    void countInsuranceCost() {
        assertEquals(loanParamsCalculator.countInsuranceCost(BigDecimal.valueOf(100000)), BigDecimal.valueOf(2000));
        assertThrows(NullPointerException.class, () -> loanParamsCalculator.countInsuranceCost(null));
    }

    @Test
    void countMonthPayment() {
        assertEquals(loanParamsCalculator.countMonthPayment(BigDecimal.valueOf(10), BigDecimal.valueOf(100000), 6), BigDecimal.valueOf(17156.14));
        assertEquals(loanParamsCalculator.countMonthPayment(BigDecimal.valueOf(7), BigDecimal.valueOf(100000), 12), BigDecimal.valueOf(8652.67));
    }

    @Test
    void countTotalAmount() {
        assertEquals(loanParamsCalculator.countTotalAmount(BigDecimal.valueOf(10000), 6), BigDecimal.valueOf(60000));
        assertEquals(loanParamsCalculator.countTotalAmount(BigDecimal.valueOf(15000), 10), BigDecimal.valueOf(150000));
        assertThrows(NullPointerException.class, () -> loanParamsCalculator.countTotalAmount(null, 10));
    }

    @Test
    void countPercentsAmount() {
        assertEquals(loanParamsCalculator.countPercentsAmount(BigDecimal.valueOf(10000), BigDecimal.valueOf(8)), BigDecimal.valueOf(66.67));
        assertEquals(loanParamsCalculator.countPercentsAmount(BigDecimal.valueOf(17155.14), BigDecimal.valueOf(7)), BigDecimal.valueOf(100.07));
    }
}