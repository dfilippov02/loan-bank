package edu.neoflex.controller.impl;

import edu.neoflex.controller.DealController;
import edu.neoflex.model.dto.FinishRegistrationDto;
import edu.neoflex.model.dto.LoanOfferDto;
import edu.neoflex.model.dto.LoanStatementRequestDto;
import edu.neoflex.service.CalculateByIdService;
import edu.neoflex.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal")
@Validated
@RequiredArgsConstructor
public class DealControllerImpl implements DealController {

    private final OfferService offerService;
    private final CalculateByIdService calculateByIdService;

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody LoanStatementRequestDto requestDto){
        return ResponseEntity.ok(offerService.getOffers(requestDto));
    }

    @PostMapping("/offer/select")
    public void selectOffers(@RequestBody LoanOfferDto offerDto){
        offerService.selectOffer(offerDto);
    }

    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@RequestBody FinishRegistrationDto finishRegistrationDto, @PathVariable UUID statementId){
        calculateByIdService.calculate(finishRegistrationDto, statementId);
    }
}
