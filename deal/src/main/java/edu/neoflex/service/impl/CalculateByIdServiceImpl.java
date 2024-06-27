package edu.neoflex.service.impl;

import edu.neoflex.clientApi.CalculatorApi;
import edu.neoflex.exception.AppException;
import edu.neoflex.model.domain.Client;
import edu.neoflex.model.domain.Credit;
import edu.neoflex.model.domain.Statement;
import edu.neoflex.model.domain.enums.ApplicationStatus;
import edu.neoflex.model.domain.enums.ChangeType;
import edu.neoflex.model.domain.enums.CreditStatus;
import edu.neoflex.model.domain.jsonb.StatusHistory;
import edu.neoflex.model.dto.CreditDto;
import edu.neoflex.model.dto.FinishRegistrationDto;
import edu.neoflex.model.dto.ScoringDataDto;
import edu.neoflex.repository.StatementRepository;
import edu.neoflex.service.CalculateByIdService;
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
        ScoringDataDto scoringDataDto = mapper.getScoringDataDto(statement, client, finishRegistrationDto.getEmployment());
        CreditDto creditDto;
        creditDto = calculatorApi.calculateCredit(scoringDataDto);

        log.info("credit successfully calculated. psk = {}, amount = {}, monthly = {}",
                creditDto.getPsk(),
                creditDto.getAmount(),
                creditDto.getMonthlyPayment());

        Credit credit = mapper.getCreditByDto(creditDto);
        credit.setStatement(statement);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        statement.getStatusHistory().add(StatusHistory.builder()
                .changeType(ChangeType.AUTOMATIC)
                .time(LocalDateTime.now())
                .status(ApplicationStatus.CC_APPROVED)
                .build());
        statement.setCredit(credit);

        log.info("credit for statement {} was successfully calculated", statementId);
    }
}
