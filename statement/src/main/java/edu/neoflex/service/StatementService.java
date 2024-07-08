package edu.neoflex.service;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {

    List<LoanOfferDto> getOffers(LoanStatementRequestDto loanStatementRequest);

    void selectOffer(LoanOfferDto loanOfferDto);
}
