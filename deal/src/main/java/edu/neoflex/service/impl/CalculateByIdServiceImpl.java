package edu.neoflex.service.impl;

import edu.neoflex.clientApi.CalculatorApi;
import edu.neoflex.config.KafkaTopics;
import edu.neoflex.domain.Client;
import edu.neoflex.domain.Credit;
import edu.neoflex.domain.Statement;
import edu.neoflex.domain.enums.ApplicationStatus;
import edu.neoflex.domain.enums.ChangeType;
import edu.neoflex.domain.enums.CreditStatus;
import edu.neoflex.domain.jsonb.StatusHistory;
import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.EmailMessageTheme;
import edu.neoflex.dto.FinishRegistrationDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.exception.AppException;
import edu.neoflex.repository.StatementRepository;
import edu.neoflex.service.CalculateByIdService;
import edu.neoflex.service.SendDocsService;
import edu.neoflex.utils.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculateByIdServiceImpl implements CalculateByIdService {

    private final StatementRepository statementRepository;
    private final EntityDtoMapper mapper;
    private final CalculatorApi calculatorApi;
    private final SendDocsService sender;


    /**
     * Вычисление данных кредита по id заявления (для вычисления берутся данные из appliedOffer)
     * @param finishRegistrationDto
     * @param statementId
     */
    @Override
    @Transactional
    public void calculate(FinishRegistrationDto finishRegistrationDto, UUID statementId) {
        log.info("Calculating credit for statement {}", statementId);

        Statement statement = statementRepository
                .findById(statementId)
                .orElseThrow(() -> new AppException(new Throwable("Invalid statement id")));

        log.info("Statement {} was loaded from db", statementId);

        if(statement.getAppliedOffer()==null){
            throw new AppException(new Throwable("Applied offer for statement not exist"));
        }

        Client client = statement.getClient();
        mapper.updateClient(client, finishRegistrationDto);
        ScoringDataDto scoringDataDto = mapper.getScoringDataDto(statement, client);
        CreditDto creditDto;
        creditDto = calculatorApi.calculateCredit(scoringDataDto);

        log.info("credit successfully calculated. psk = {}, amount = {}, monthly = {}",
                creditDto.getPsk(),
                creditDto.getAmount(),
                creditDto.getMonthlyPayment());

        Credit credit = mapper.getCreditByDto(creditDto);
        credit.setStatement(statement);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.getStatusHistory().add(StatusHistory.builder()
                .changeType(ChangeType.AUTOMATIC)
                .time(LocalDateTime.now())
                .status(ApplicationStatus.CC_APPROVED)
                .build());
        statement.setCredit(credit);

        log.info("credit for statement {} was successfully calculated", statementId);

        sender.send(EmailMessageTheme.CREATE_DOCUMENTS, KafkaTopics.CREATE_DOCUMENTS, statementId);
    }
}
