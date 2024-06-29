package edu.neoflex.controller.impl;

import edu.neoflex.controller.StatementApi;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.service.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/statement")
@Validated
@RequiredArgsConstructor
public class StatementController implements StatementApi {

    private final StatementService statementSerice;

    /**
     * Получение предложений по кредиту
     *
     * @param loanStatementRequest -
     * @return - 4 предложения по кредиту от худшего к лучшему
     */
    @Override
    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequest) {
        return ResponseEntity.ok().body(statementSerice.getOffers(loanStatementRequest));
    }

    /**
     * Выбор кредитного предложения
     * @param loanOfferDto -
     */
    @Override
    @PostMapping("/offer")
    public void selectOffers(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        statementSerice.selectOffer(loanOfferDto);
    }

}
