package edu.neoflex.service.impl;

import edu.neoflex.clientApi.CalculatorApi;
import edu.neoflex.config.KafkaTopics;
import edu.neoflex.domain.Client;
import edu.neoflex.domain.Statement;
import edu.neoflex.domain.enums.ApplicationStatus;
import edu.neoflex.domain.enums.ChangeType;
import edu.neoflex.domain.jsonb.StatusHistory;
import edu.neoflex.dto.EmailMessageTheme;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.exception.AppException;
import edu.neoflex.repository.ClientRepository;
import edu.neoflex.repository.StatementRepository;
import edu.neoflex.service.OfferService;
import edu.neoflex.service.SendDocsService;
import edu.neoflex.utils.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final CalculatorApi calculatorApi;
    private final EntityDtoMapper mapper;
    private final SendDocsService sendDocsService;

    /**
     * Создать заявление и получить предложения по кредиту с помощью Calculator
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public List<LoanOfferDto> getOffers(LoanStatementRequestDto requestDto) {
        log.info("Calculating offers for {} {}, passport {} {}",
                requestDto.getLastName(),
                requestDto.getFirstName(),
                requestDto.getPassportNumber(),
                requestDto.getPassportSeries());

        Client client = clientRepository.save(mapper.getClientByDto(requestDto));
        log.info("Client was saved with id {}", client.getClientId());

        Statement statement = statementRepository.save(mapper.getStatementForClient(client));
        log.info("Statement was saved with id {}", statement.getStatementId());

        List<LoanOfferDto> offers = calculatorApi.getOffers(requestDto);
        offers.forEach(o -> o.setStatementId(statement.getStatementId()));
        log.info("Offers for statement {} were received", statement.getStatementId());

        return offers;
    }

    /**
     * Выбрать предложение по кредиту и установить в заявление
     * @param offerDto
     */
    @Override
    @Transactional
    public void selectOffer(LoanOfferDto offerDto) {

        log.info("Updating selected offer for statement {}", offerDto.getStatementId());

        Statement statement = statementRepository
                .findById(offerDto.getStatementId())
                .orElseThrow(() -> new AppException(new Throwable("Invalid statement id")));

        log.info("Statement {} was loaded from db", offerDto.getStatementId());

        statement.setStatus(ApplicationStatus.APPROVED);

        statement.getStatusHistory().add(StatusHistory.builder()
                .changeType(ChangeType.AUTOMATIC)
                .time(LocalDateTime.now())
                .status(ApplicationStatus.APPROVED)
                .build());

        log.info("Statement {} status was updated to APPROVED", statement.getStatementId());

        sendDocsService.send(EmailMessageTheme.FINISH_REGISTRATION, KafkaTopics.FINISH_REGISTRATION, statement.getStatementId());

        statement.setAppliedOffer(offerDto);
    }

    /**
     * Подписание документов
     * @param sesCode - код подписания
     * @param statementId - id заявления
     */
    @Override
    @Transactional
    public void signStatement(String sesCode, UUID statementId) {

        log.info("Signing statement {}", statementId);

        Statement statement = statementRepository
                .findById(statementId)
                .orElseThrow(() -> new AppException(new Throwable("Invalid statement id")));

        if(statement.getSesCode().equals(sesCode)){
            statement.setSignDate(LocalDateTime.now());
            statement.setStatus(ApplicationStatus.DOCUMENT_SIGNED);
            sendDocsService.send(EmailMessageTheme.CREDIT_ISSUED, KafkaTopics.CREDIT_ISSUED, statementId);
            log.info("Statement {} was signed", statementId);
        } else {
            log.info("Wrong ses code for statement {}", statementId);
            throw new AppException(new Throwable("Invalid ses code"));
        }
    }
}
