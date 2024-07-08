package edu.neoflex.utils;

import edu.neoflex.domain.Client;
import edu.neoflex.domain.Credit;
import edu.neoflex.domain.Statement;
import edu.neoflex.domain.enums.ApplicationStatus;
import edu.neoflex.domain.enums.ChangeType;
import edu.neoflex.domain.enums.CreditStatus;
import edu.neoflex.domain.jsonb.StatusHistory;
import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.FinishRegistrationDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class EntityDtoMapper {

    private final ClientMapper clientMapper = ClientMapper.INSTANCE;
    private final ClientToScoringDataMapper scoringDataMapper = ClientToScoringDataMapper.INSTANCE;
    private final CreditMapper creditMapper = CreditMapper.INSTANCE;
    private final EmploymentMapper employmentMapper = EmploymentMapper.INSTANCE;

    /**
     * Получение сущности client из requestDto
     * @param requestDto
     * @return
     */
    public Client getClientByDto(LoanStatementRequestDto requestDto) {
        Client client = clientMapper.toEntity(requestDto);
        client.setClientId(UUID.randomUUID());
        client.getPassport().setPassport_uuid(UUID.randomUUID());
        return client;
    }

    /**
     * Получение ScoringDataDto из данных клиента и заявления
     * @param statement
     * @param client
     * @return
     */
    public ScoringDataDto getScoringDataDto(Statement statement, Client client){
        ScoringDataDto scoringDataDto = scoringDataMapper.map(client, statement);
        return scoringDataDto;
    }

    /**
     * Формирование сущности кредита из DTO
     * @param creditDto
     * @return
     */
    public Credit getCreditByDto(CreditDto creditDto){
        Credit credit = creditMapper.toEntity(creditDto);
        credit.setCreditId(UUID.randomUUID());
        credit.setCreditStatus(CreditStatus.CALCULATED);
        return credit;
    }


    /**
     * Добавление данных в сущость client из finishRegistrationDto
     * @param client
     * @param finishRegistrationDto
     */
    public void updateClient(Client client, FinishRegistrationDto finishRegistrationDto) {
        client.setEmployment(employmentMapper.toEntity(finishRegistrationDto.getEmployment()));
        client.setDependentAmount(finishRegistrationDto.getDependentAmount());
        client.setGender(finishRegistrationDto.getGender());
        client.setMaritalStatus(finishRegistrationDto.getMaritalStatus());
        client.getPassport().setIssueDate(finishRegistrationDto.getPassportIssueDate());
        client.getPassport().setIssueBranch(finishRegistrationDto.getPassportIssueBranch());
        client.setAccountNumber(finishRegistrationDto.getAccountNumber());
    }

    /**
     * Получить сущность statement
     * @param client
     * @return
     */
    public Statement getStatementForClient(Client client){
        Random random = new Random();
        return Statement.builder()
                .client(client)
                .statementId(UUID.randomUUID())
                .sesCode(String.valueOf(random.nextInt(100000, 999999)))
                .status(ApplicationStatus.PREAPPROVAL)
                .statusHistory(List.of(StatusHistory.builder()
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .status(ApplicationStatus.PREAPPROVAL)
                        .build()))
                .build();
    }
}
