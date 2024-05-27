package edu.neoflex.service;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

public interface OffersService {

    List<LoanOfferDto> createOffers(LoanStatementRequestDto statementRequest);
}
