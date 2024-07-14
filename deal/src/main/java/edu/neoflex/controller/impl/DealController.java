package edu.neoflex.controller.impl;

import edu.neoflex.config.KafkaTopics;
import edu.neoflex.controller.DealApi;
import edu.neoflex.dto.EmailMessageTheme;
import edu.neoflex.dto.FinishRegistrationDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.service.CalculateByIdService;
import edu.neoflex.service.OfferService;
import edu.neoflex.service.impl.SendDocsServiceImpl;
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
public class DealController implements DealApi {

    private final OfferService offerService;
    private final CalculateByIdService calculateByIdService;
    private final SendDocsServiceImpl docsService;

    /**
     * Получение кредитных предложений
     * @param requestDto
     * @return
     */
    @Override
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody @Valid LoanStatementRequestDto requestDto){
        return ResponseEntity.ok(offerService.getOffers(requestDto));
    }

    /**
     * Выбор предложения для заявления
     * @param offerDto
     */
    @Override
    @PostMapping("/offer/select")
    public void selectOffers(@RequestBody @Valid LoanOfferDto offerDto){
        offerService.selectOffer(offerDto);
    }

    /**
     * Рассчет данных кредита
     * @param finishRegistrationDto
     * @param statementId
     */
    @Override
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@RequestBody @Valid FinishRegistrationDto finishRegistrationDto, @PathVariable UUID statementId){
        calculateByIdService.calculate(finishRegistrationDto, statementId);
    }

    /**
     * Запрос на отправку документов
     * @param statementId
     */
    @Override
    @PostMapping("/document/{statementId}/send")
    public void send(@PathVariable UUID statementId) {
        docsService.send(EmailMessageTheme.SEND_DOCUMENTS, KafkaTopics.SEND_DOCUMENTS, statementId);
    }

    /**
     * Запрос на подписание документов
     * @param statementId
     */
    @Override
    @PostMapping("/document/{statementId}/sign")
    public void sign(@PathVariable UUID statementId) {
        docsService.send(EmailMessageTheme.SEND_SES, KafkaTopics.SEND_SES, statementId);
    }

    /**
     * Завершение подписания
     * @param statementId
     */
    @Override
    @PostMapping("/document/{statementId}/code")
    public void code(@PathVariable UUID statementId, @RequestParam String code) {
        offerService.signStatement(code, statementId);
    }
}
