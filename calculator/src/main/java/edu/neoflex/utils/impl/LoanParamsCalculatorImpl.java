package edu.neoflex.utils.impl;

import edu.neoflex.utils.LoanParamsCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class LoanParamsCalculatorImpl implements LoanParamsCalculator {

    @Value("${loan.rate}")
    BigDecimal loanBaseRate;

    @Value("${loan.insurance.cost}")
    BigDecimal loanInsuranceCost;

    @Value("${loan.salary-client.discount}")
    BigDecimal salaryClientDiscount;


    @Override
    public BigDecimal countLoanRate(Boolean isSalaryClient, Boolean isInsuranceEnabled) {
        BigDecimal loanRate = loanBaseRate;
        if(isSalaryClient){
            loanRate = loanBaseRate.subtract(salaryClientDiscount);
        } if(isInsuranceEnabled) {
            loanRate = loanBaseRate.subtract(BigDecimal.valueOf(2));
        }
        return loanRate;
    }


    @Override
    public BigDecimal countInsuranceCost(BigDecimal requestedAmount) {
        return requestedAmount
                .multiply(loanInsuranceCost)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
    }


    @Override
    public BigDecimal countMonthPayment(BigDecimal loanRate, BigDecimal amount, int term) {
        BigDecimal monthPercentRate = loanRate.divide(BigDecimal.valueOf(1200), MathContext.DECIMAL128);
        return amount
                .multiply(monthPercentRate
                        .add(monthPercentRate
                                        .divide(BigDecimal.ONE
                                                .add(monthPercentRate)
                                                .pow(term, MathContext.DECIMAL128)
                                                .subtract(BigDecimal.ONE), MathContext.DECIMAL128),
                                MathContext.DECIMAL128))
                .setScale(2, RoundingMode.HALF_EVEN);
    }


    @Override
    public BigDecimal countTotalAmount(BigDecimal monthPayment, int term) {
        return monthPayment.multiply(BigDecimal.valueOf(term));
    }


    @Override
    public BigDecimal countPercentsAmount(BigDecimal remainAmount, BigDecimal loanRate) {
        BigDecimal monthLoanRate = loanRate.divide(BigDecimal.valueOf(1200), MathContext.DECIMAL128);
        return remainAmount.multiply(monthLoanRate).setScale(2, RoundingMode.HALF_EVEN);
    }

}
