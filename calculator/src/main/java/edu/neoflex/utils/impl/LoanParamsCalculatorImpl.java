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

    @Value("${loan.insurance.discount}")
    BigDecimal loanInsuranceDiscount;

    @Value("${loan.salary-client.discount}")
    BigDecimal salaryClientDiscount;


    /**
     * Расчет ставки по кредиту
     * @param isSalaryClient - зарплатный клиент
     * @param isInsuranceEnabled - страховка включена
     * @return - ставка в процентах
     */
    @Override
    public BigDecimal countLoanRate(Boolean isSalaryClient, Boolean isInsuranceEnabled) {
        BigDecimal loanRate = loanBaseRate;
        if(isSalaryClient){
            loanRate = loanBaseRate.subtract(salaryClientDiscount);
        } if(isInsuranceEnabled) {
            loanRate = loanRate.subtract(loanInsuranceDiscount);
        }
        return loanRate;
    }


    /**
     * Расчет величины стоимости страховки
     * @param requestedAmount - сумма займа
     * @return - стоимость страховки
     */
    @Override
    public BigDecimal countInsuranceCost(BigDecimal requestedAmount) {
        return requestedAmount
                .multiply(loanInsuranceCost)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
    }


    /**
     * Расчет ежемесячного платежа
     * @param loanRate - ставка по кредиту
     * @param amount - объем займа
     * @param term - период займа в месяцах
     * @return - сумма ежемесячного платежа
     */
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


    /**
     * Расчет полной стоимости кредита
     * @param monthPayment - сумма ежемесячного платежа
     * @param term - период
     * @return - полная сумма кредита
     */
    @Override
    public BigDecimal countTotalAmount(BigDecimal monthPayment, int term) {
        return monthPayment.multiply(BigDecimal.valueOf(term));
    }


    /**
     * рассчет доли платы по процентам в сумме ежемесячного платежа
     * @param remainAmount - остаток платежа
     * @param loanRate - кредитная ставка
     * @return - сумма выплаты по процентам в конкретный месяц
     */
    @Override
    public BigDecimal countPercentsAmount(BigDecimal remainAmount, BigDecimal loanRate) {
        BigDecimal monthLoanRate = loanRate.divide(BigDecimal.valueOf(1200), MathContext.DECIMAL128);
        return remainAmount.multiply(monthLoanRate).setScale(2, RoundingMode.HALF_EVEN);
    }

}
