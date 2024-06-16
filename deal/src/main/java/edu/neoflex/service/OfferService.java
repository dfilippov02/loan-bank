package edu.neoflex.service;

import edu.neoflex.model.dto.LoanOfferDto;
import edu.neoflex.model.dto.LoanStatementRequestDto;

import java.util.List;

public interface OfferService {

    public List<LoanOfferDto> getOffers(LoanStatementRequestDto requestDto);

    public void selectOffer(LoanOfferDto offerDto);

}
