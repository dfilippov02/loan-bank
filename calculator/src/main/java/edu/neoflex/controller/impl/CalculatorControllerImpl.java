package edu.neoflex.controller.impl;

import edu.neoflex.controller.CalculatorController;
import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.service.LoanService;
import edu.neoflex.service.OffersService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CalculatorControllerImpl implements CalculatorController {

    private final LoanService loanService;
    private final OffersService offersService;

    @Override
    @PostMapping("/offers")
    public List<LoanOfferDto> getOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequest) {
        return offersService.createOffers(loanStatementRequest);
    }

    @Override
    @PostMapping("/calc")
    public CreditDto getCredit(@RequestBody @Valid ScoringDataDto scoringData) {
        return loanService.calculateLoan(scoringData);
    }

}
