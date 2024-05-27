package edu.neoflex.controller;

import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.service.LoanService;
import edu.neoflex.service.OffersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/calculator")
@Validated
public class CalculatorController {

    LoanService loanService;
    OffersService offersService;

    public CalculatorController(LoanService loanService, OffersService offersService) {
        this.loanService = loanService;
        this.offersService = offersService;
    }

    @PostMapping("/offers")
    List<LoanOfferDto> getOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequest) {
        return offersService.createOffers(loanStatementRequest);
    }

    @PostMapping("/calc")
    CreditDto getCredit(@RequestBody @Valid ScoringDataDto scoringData) {
        return loanService.calculateLoan(scoringData);
    }

}
