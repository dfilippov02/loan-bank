package edu.neoflex.utils;

import edu.neoflex.model.domain.Client;
import edu.neoflex.model.domain.Credit;
import edu.neoflex.model.domain.Statement;
import edu.neoflex.model.domain.enums.ApplicationStatus;
import edu.neoflex.model.domain.enums.ChangeType;
import edu.neoflex.model.domain.enums.CreditStatus;
import edu.neoflex.model.domain.jsonb.Employment;
import edu.neoflex.model.domain.jsonb.Passport;
import edu.neoflex.model.domain.jsonb.StatusHistory;
import edu.neoflex.model.dto.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class EntityDtoMapper {

    /**
     * Получение сущности client из requestDto
     * @param requestDto
     * @return
     */
    public Client getClient(LoanStatementRequestDto requestDto) {
        return Client.builder()
                .clientId(UUID.randomUUID())
                .accountNumber(UUID.randomUUID())
                .birthdate(requestDto.getBirthdate())
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .middleName(requestDto.getMiddleName())
                .passport(Passport.builder()
                        .passport_uuid(UUID.randomUUID())
                        .number(requestDto.getPassportNumber())
                        .series(requestDto.getPassportSeries())
                        .build())
                .build();
    }

    /**
     * Получение ScoringDataDto из данных клиента и заявления
     * @param statement
     * @param client
     * @param employment
     * @return
     */
    public ScoringDataDto getScoringDataDto(Statement statement, Client client, EmploymentDto employment){
        return ScoringDataDto.builder()
                .amount(statement.getAppliedOffer().getRequestedAmount())
                .term(statement.getAppliedOffer().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .dependentAmount(client.getDependentAmount())
                .accountNumber(client.getAccountNumber().toString())
                .gender(client.getGender())
                .birthdate(client.getBirthdate())
                .employment(employment)
                .maritalStatus(client.getMaritalStatus())
                .passportNumber(client.getPassport().getNumber())
                .passportSeries(client.getPassport().getSeries())
                .passportIssueBranch(client.getPassport().getIssueBranch())
                .passportIssueDate(client.getPassport().getIssueDate())
                .isSalaryClient(statement.getAppliedOffer().getIsSalaryClient())
                .isInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled())
                .build();

    }

    /**
     * Формирование сущности кредита из DTO
     * @param creditDto
     * @return
     */
    public Credit getCredit(CreditDto creditDto){
        return Credit.builder()
                .creditId(UUID.randomUUID())
                .creditStatus(CreditStatus.CALCULATED)
                .amount(creditDto.getAmount())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .psk(creditDto.getPsk())
                .paymentSchedule(creditDto.getPaymentSchedule())
                .insuranceEnabled(creditDto.getIsInsuranceEnabled())
                .isSalaryClient(creditDto.getIsSalaryClient())
                .rate(creditDto.getRate())
                .term(creditDto.getTerm())
                .build();
    }


    /**
     * Формирование сущности Employment из DTO
     * @param employmentDto
     * @return
     */
    public Employment getEmployment(EmploymentDto employmentDto) {
        return Employment.builder()
                .employment_uuid(UUID.randomUUID())
                .status(employmentDto.getEmploymentStatus())
                .employmentInn(employmentDto.getEmployerINN())
                .salary(employmentDto.getSalary())
                .position(employmentDto.getPosition())
                .workExperienceTotal(employmentDto.getWorkExperienceTotal())
                .workExperienceCurrent(employmentDto.getWorkExperienceCurrent())
                .build();
    }

    /**
     * Добавление данных в сущость client из finishRegistrationDto
     * @param client
     * @param finishRegistrationDto
     */
    public void updateClient(Client client, FinishRegistrationDto finishRegistrationDto) {
        client.setEmployment(getEmployment(finishRegistrationDto.getEmployment()));
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
    public Statement getStatement(Client client){
        Random random = new Random();
        return Statement.builder()
                .client(client)
                .statementId(UUID.randomUUID())
                .sesCode(String.valueOf(random.nextInt(100000, 999999)))
                .applicationStatus(ApplicationStatus.PREAPPROVAL)
                .statusHistory(List.of(StatusHistory.builder()
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .status(ApplicationStatus.PREAPPROVAL)
                        .build()))
                .build();
    }
}
