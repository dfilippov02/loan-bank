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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal")
@Validated
@RequiredArgsConstructor
public class DealControllerImpl implements DealController {

    private final OfferService offerService;
    private final CalculateByIdService calculateByIdService;

    /**
     * Получение кредитных предложений
     * @param requestDto
     * @return
     */
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody @Valid LoanStatementRequestDto requestDto){
        return ResponseEntity.ok(offerService.getOffers(requestDto));
    }

    /**
     * Выбор предложения для заявления
     * @param offerDto
     */
    @PostMapping("/offer/select")
    public void selectOffers(@RequestBody @Valid LoanOfferDto offerDto){
        offerService.selectOffer(offerDto);
    }

    /**
     * Рассчет данных кредита
     * @param finishRegistrationDto
     * @param statementId
     */
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@RequestBody @Valid FinishRegistrationDto finishRegistrationDto, @PathVariable UUID statementId){
        calculateByIdService.calculate(finishRegistrationDto, statementId);
    }
}
