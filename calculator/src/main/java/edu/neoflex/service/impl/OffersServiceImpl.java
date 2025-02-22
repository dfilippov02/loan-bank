package edu.neoflex.service.impl;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.service.OffersService;
import edu.neoflex.utils.LoanParamsCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OffersServiceImpl implements OffersService {

    private final LoanParamsCalculator loanParamsCalculator;

    /**
     * Получение списка из 4 кредитных предложений
     * @param statementRequest - данные клиента
     * @return - 4 предложения по убыванию процентной ставки
     */
    @Override
    public List<LoanOfferDto> createOffers(LoanStatementRequestDto statementRequest) {
        log.info("Creating loan offers for {}", statementRequest);
        List<LoanOfferDto> offerDtoList = List.of(
                createOffer(true, true, statementRequest),
                createOffer(false, true, statementRequest),
                createOffer(true, false, statementRequest),
                createOffer(false, false, statementRequest)
        );
        log.info("Created 4 loan offers for {}", statementRequest.getFirstName());
        return offerDtoList.stream()
                .sorted(Comparator.comparing(LoanOfferDto::getRate).reversed())
                .collect(Collectors.toList());
    }


    /**
     * Создание предложения в зависимости от входных параметров
     * @param isSalaryClient - клиент является зарплатным
     * @param isInsuranceEnabled - страховка включена
     * @param statementRequest - данные для расчета кредита
     * @return - предложение с расчитанными параметрами
     */
    private LoanOfferDto createOffer(Boolean isSalaryClient, Boolean isInsuranceEnabled, LoanStatementRequestDto statementRequest) {
        BigDecimal requestedAmount = statementRequest.getAmount();
        BigDecimal requestedWithInsurance = requestedAmount;
        if (isInsuranceEnabled)
            requestedWithInsurance = requestedAmount.add(loanParamsCalculator.countInsuranceCost(requestedAmount));

        LoanOfferDto offer = LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .isSalaryClient(isSalaryClient)
                .isInsuranceEnabled(isInsuranceEnabled)
                .requestedAmount(statementRequest.getAmount())
                .term(statementRequest.getTerm())
                .rate(loanParamsCalculator.countLoanRate(isSalaryClient, isInsuranceEnabled))
                .build();

        offer.setMonthlyPayment(loanParamsCalculator.countMonthPayment(offer.getRate(), requestedWithInsurance, statementRequest.getTerm()));
        offer.setTotalAmount(loanParamsCalculator.countTotalAmount(offer.getMonthlyPayment(), statementRequest.getTerm()));

        log.info("Loan offer was created. rate - {}, total - {}, monthly payment - {}",
                offer.getRate(),
                offer.getTotalAmount(),
                offer.getMonthlyPayment());

        return offer;
    }
}
