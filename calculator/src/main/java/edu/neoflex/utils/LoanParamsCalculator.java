package edu.neoflex.utils;

import java.math.BigDecimal;

public interface LoanParamsCalculator {

    BigDecimal countLoanRate(Boolean isSalaryClient, Boolean isInsuranceEnabled);

    BigDecimal countInsuranceCost(BigDecimal requestedAmount);

    BigDecimal countMonthPayment(BigDecimal loanRate, BigDecimal amount, int term);

    BigDecimal countTotalAmount(BigDecimal monthPayment, int term);

    BigDecimal countPercentsAmount(BigDecimal remainAmount, BigDecimal loanRate);

}