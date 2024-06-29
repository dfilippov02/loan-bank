package edu.neoflex.service;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

public interface OfferService {

    public List<LoanOfferDto> getOffers(LoanStatementRequestDto requestDto);

    public void selectOffer(LoanOfferDto offerDto);

}
