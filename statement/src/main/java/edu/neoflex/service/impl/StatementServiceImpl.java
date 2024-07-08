package edu.neoflex.service.impl;

import edu.neoflex.api.DealApi;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.service.StatementService;
import edu.neoflex.utils.PrescoringProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatementServiceImpl implements StatementService {

    private final PrescoringProcessor prescoringProcessor;
    private final DealApi dealApi;


    /**
     * Получить предложения из МС сделка
     * @param loanStatementRequest
     * @return
     */
    @Override
    public List<LoanOfferDto> getOffers(LoanStatementRequestDto loanStatementRequest) {
        log.info("Requesting offers for {} {} {} {}",
                loanStatementRequest.getFirstName(),
                loanStatementRequest.getLastName(),
                loanStatementRequest.getPassportSeries(),
                loanStatementRequest.getPassportNumber());

        prescoringProcessor.prescoring(loanStatementRequest);

        List<LoanOfferDto> offers = dealApi.getOffers(loanStatementRequest);

        log.info("Offers for {} {} was received",
                loanStatementRequest.getFirstName(),
                loanStatementRequest.getLastName());

        return offers;
    }

    /**
     * Выбрать предложение в МС сделка
     * @param loanOfferDto
     */
    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("Select offer for {}", loanOfferDto.getStatementId());

        dealApi.selectOffers(loanOfferDto);

        log.info("Offer for {} was successfully updated", loanOfferDto.getStatementId());
    }
}
