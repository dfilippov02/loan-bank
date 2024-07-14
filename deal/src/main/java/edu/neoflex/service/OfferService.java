package edu.neoflex.service;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface OfferService {

    public List<LoanOfferDto> getOffers(LoanStatementRequestDto requestDto);

    public void selectOffer(LoanOfferDto offerDto);

    @Transactional
    void signStatement(String sesCode, UUID statementId);
}
