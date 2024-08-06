package edu.neoflex.controller;

import edu.neoflex.client.DealClient;
import edu.neoflex.client.StatementClient;
import edu.neoflex.dto.FinishRegistrationDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GatewayController implements GatewayApi{

    private final DealClient dealClient;
    private final StatementClient statementClient;

    /**
     * Получение предложений по кредиту
     *
     * @param loanStatementRequest -
     * @return - 4 предложения по кредиту от худшего к лучшему
     */
    @Override
    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequest) {
        log.info("Requesting loan offers for statement {}", loanStatementRequest);
        return statementClient.getOffers(loanStatementRequest);
    }

    /**
     * Выбор кредитного предложения
     * @param loanOfferDto -
     */
    @Override
    @PostMapping("/offer")
    public void selectOffers(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("select offer {}", loanOfferDto);
        statementClient.selectOffers(loanOfferDto);
    }

    /**
     * Рассчет данных кредита
     * @param finishRegistrationDto
     * @param statementId
     */
    @Override
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@RequestBody @Valid FinishRegistrationDto finishRegistrationDto, @PathVariable UUID statementId){
        log.info("Calculating credit for statement {}", finishRegistrationDto);
        dealClient.calculateCredit(finishRegistrationDto, statementId);
    }

    /**
     * Запрос на отправку документов
     * @param statementId
     */
    @Override
    @PostMapping("/document/{statementId}/send")
    public void send(@PathVariable UUID statementId) {
        log.info("Sending docs for statement {}", statementId);
        dealClient.send(statementId);
    }

    /**
     * Запрос на подписание документов
     * @param statementId
     */
    @Override
    @PostMapping("/document/{statementId}/sign")
    public void sign(@PathVariable UUID statementId) {
        log.info("Sending ses code for statement {}", statementId);
        dealClient.sign(statementId);
    }

    /**
     * Завершение подписания
     * @param statementId
     */
    @Override
    @PostMapping("/document/{statementId}/code")
    public void code(@PathVariable UUID statementId, @RequestParam String code) {
        log.info("Sign docs for {}", statementId);
        dealClient.code(statementId, code);
    }


}
