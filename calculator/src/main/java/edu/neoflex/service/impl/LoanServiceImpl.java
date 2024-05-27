package edu.neoflex.service.impl;

import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.PaymentScheduleElementDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.service.LoanService;
import edu.neoflex.utils.LoanParamsCalculator;
import edu.neoflex.utils.ScoringProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
@Log4j2
public class LoanServiceImpl implements LoanService {

    ScoringProcessor scoringProcessor;
    LoanParamsCalculator loanParamsCalculator;

    public LoanServiceImpl(ScoringProcessor scoringProcessor, LoanParamsCalculator loanParamsCalculator) {
        this.scoringProcessor = scoringProcessor;
        this.loanParamsCalculator = loanParamsCalculator;
    }


    @Override
    public CreditDto calculateLoan(ScoringDataDto scoringData) {
        log.info("Loan creating for {} {}, account number - {}, credit amount - {}",
                scoringData.getFirstName(),
                scoringData.getLastName(),
                scoringData.getAccountNumber(),
                scoringData.getAmount());
        BigDecimal loanRate = scoringProcessor.scoring(scoringData);
        BigDecimal monthPayment = loanParamsCalculator.countMonthPayment(loanRate, scoringData.getAmount(), scoringData.getTerm());
        BigDecimal psk = loanParamsCalculator.countTotalAmount(monthPayment, scoringData.getTerm());

        List<PaymentScheduleElementDto> paymentScheduleElements = getPaymentSchedule(scoringData, loanRate, monthPayment, scoringData.getAmount());

        CreditDto creditDto = CreditDto.builder()
                .amount(scoringData.getAmount())
                .term(scoringData.getTerm())
                .monthlyPayment(monthPayment)
                .rate(loanRate)
                .psk(psk)
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .isSalaryClient(scoringData.getIsSalaryClient())
                .paymentSchedule(paymentScheduleElements).build();

        log.info("Loan for {} was created. rate - {}, total - {}, monthPayment - {}",
                scoringData.getAccountNumber(),
                creditDto.getRate(),
                creditDto.getPsk(),
                creditDto.getMonthlyPayment());
        return creditDto;

    }

    private List<PaymentScheduleElementDto> getPaymentSchedule(ScoringDataDto scoringData, BigDecimal loanRate, BigDecimal monthPayment, BigDecimal remainAmount) {
        log.info("Payment schedule creating for {} {}, account number - {}, credit amount - {}",
                scoringData.getFirstName(),
                scoringData.getLastName(),
                scoringData.getAccountNumber(),
                scoringData.getAmount());

        List<PaymentScheduleElementDto> paymentScheduleElements = new LinkedList<>();
        LocalDate currentMonth = LocalDate.now();
        for (int i = 0; i < scoringData.getTerm(); i++) {
            BigDecimal interestPayment = loanParamsCalculator.countPercentsAmount(remainAmount, loanRate);
            BigDecimal debtPayment = monthPayment.subtract(interestPayment);
            PaymentScheduleElementDto paymentScheduleElement = PaymentScheduleElementDto.builder()
                    .totalPayment(monthPayment)
                    .date(currentMonth.plusMonths(i))
                    .number(i+1)
                    .interestPayment(interestPayment)
                    .debtPayment(monthPayment.subtract(interestPayment))
                    .remainingDebt(remainAmount.subtract(debtPayment))
                    .build();

            remainAmount = remainAmount.subtract(paymentScheduleElement.getDebtPayment());
            paymentScheduleElements.add(paymentScheduleElement);
        }
        return paymentScheduleElements;
    }


}
