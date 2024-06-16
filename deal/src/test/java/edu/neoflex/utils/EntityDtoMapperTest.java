package edu.neoflex.utils;

import edu.neoflex.model.domain.Client;
import edu.neoflex.model.domain.Credit;
import edu.neoflex.model.domain.Statement;
import edu.neoflex.model.domain.enums.ApplicationStatus;
import edu.neoflex.model.domain.enums.EmploymentPosition;
import edu.neoflex.model.domain.enums.EmploymentStatus;
import edu.neoflex.model.domain.enums.MaritalStatus;
import edu.neoflex.model.domain.jsonb.Employment;
import edu.neoflex.model.domain.jsonb.Passport;
import edu.neoflex.model.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EntityDtoMapperTest {

    @InjectMocks
    EntityDtoMapper mapper;

    private UUID id = UUID.fromString("8f8daf0a-6f7f-49da-8288-21c445c86fd5");

    @Test
    public void getClient() {
        LoanStatementRequestDto requestDTO = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(6)
                .firstName("Daniel")
                .lastName("Rudkovsky")
                .middleName("Ivanov")
                .email("ivanov@mail.ru")
                .birthdate(LocalDate.of(2000, 12, 12))
                .passportSeries("0000")
                .passportNumber("0000000")
                .build();

        Client client = mapper.getClient(requestDTO);


        assertEquals(requestDTO.getFirstName(), client.getFirstName());
        assertEquals(requestDTO.getMiddleName(), client.getMiddleName());
        assertEquals(requestDTO.getLastName(), client.getLastName());
        assertEquals(requestDTO.getBirthdate(), client.getBirthdate());
        assertEquals(requestDTO.getPassportSeries(), client.getPassport().getSeries());
        assertEquals(requestDTO.getPassportNumber(), client.getPassport().getNumber());
    }


    @Test
    public void getScoringDataDto(){

        Client client = Client.builder()
                .clientId(id)
                .accountNumber(UUID.randomUUID())
                .passport(Passport.builder()
                        .number("000000")
                        .series("0000")
                        .issueBranch("")
                        .passport_uuid(UUID.randomUUID())
                        .issueDate(LocalDate.of(2020,12,12))
                        .build())
                .maritalStatus(MaritalStatus.MARRIED)
                .firstName("Daniel")
                .employment(Employment.builder().build())
                .build();

        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.UNEMPLOYED)
                .employerINN("")
                .position(EmploymentPosition.TOP_LEVEL_MANAGER)
                .workExperienceCurrent(18)
                .workExperienceTotal(30)
                .build();


        LoanOfferDto loanOfferDto = LoanOfferDto.builder()
                .requestedAmount(BigDecimal.valueOf(100000))
                .monthlyPayment(BigDecimal.valueOf(17000))
                .rate(BigDecimal.valueOf(5))
                .term(6)
                .totalAmount(BigDecimal.valueOf(105000))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();

        Statement statement = Statement.builder()
                .statusHistory(new ArrayList<>())
                .statementId(id)
                .client(Client.builder().build())
                .appliedOffer(loanOfferDto)
                .applicationStatus(ApplicationStatus.APPROVED)
                .build();

        ScoringDataDto scoringDataDto = mapper.getScoringDataDto(statement, client, employmentDto);

        assertEquals(scoringDataDto.getAmount(), loanOfferDto.getRequestedAmount());
        assertEquals(scoringDataDto.getFirstName(), client.getFirstName());
        assertEquals(scoringDataDto.getMaritalStatus(), client.getMaritalStatus());

        assertThrows(NullPointerException.class, () -> mapper.getScoringDataDto(null, null, employmentDto));

    }


    @Test
    public void getCredit(){
        CreditDto creditDto = CreditDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .monthlyPayment(BigDecimal.valueOf(17000))
                .paymentSchedule(new ArrayList<>())
                .psk(BigDecimal.valueOf(105000))
                .rate(BigDecimal.valueOf(9))
                .term(6)
                .build();

        Credit credit = mapper.getCredit(creditDto);

        assertEquals(credit.getAmount(), creditDto.getAmount());
        assertEquals(credit.getIsSalaryClient(), creditDto.getIsSalaryClient());
        assertEquals(credit.getMonthlyPayment(), creditDto.getMonthlyPayment());
        assertEquals(credit.getPsk(), creditDto.getPsk());
        assertEquals(credit.getRate(), creditDto.getRate());

        assertThrows(NullPointerException.class, () -> mapper.getCredit(null));
    }

    @Test
    public void getEmployment(){
        EmploymentDto employmentDto = EmploymentDto.builder()
                .workExperienceTotal(18)
                .workExperienceCurrent(30)
                .position(EmploymentPosition.TOP_LEVEL_MANAGER)
                .employerINN("00000000000")
                .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                .salary(BigDecimal.valueOf(40000))
                .build();

        Employment employment = mapper.getEmployment(employmentDto);

        assertEquals(employment.getEmploymentInn(), employmentDto.getEmployerINN());
        assertEquals(employment.getPosition(), employmentDto.getPosition());
        assertEquals(employment.getSalary(), employmentDto.getSalary());

        assertThrows(NullPointerException.class, () -> mapper.getEmployment(null));
    }


    @Test
    public void getStatement(){
        Client client = Client.builder().build();

        Statement statement = mapper.getStatement(client);

        assertEquals(statement.getApplicationStatus(), ApplicationStatus.PREAPPROVAL);
        assertEquals(statement.getClient(), client);
        assertEquals(statement.getStatusHistory().size(), 1);
    }



}