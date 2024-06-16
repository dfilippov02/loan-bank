package edu.neoflex.service.impl;

import edu.neoflex.clientApi.CalculatorApi;
import edu.neoflex.exception.AppException;
import edu.neoflex.model.domain.Credit;
import edu.neoflex.model.domain.Statement;
import edu.neoflex.model.domain.enums.CreditStatus;
import edu.neoflex.model.domain.enums.Gender;
import edu.neoflex.model.domain.enums.MaritalStatus;
import edu.neoflex.model.dto.CreditDto;
import edu.neoflex.model.dto.EmploymentDto;
import edu.neoflex.model.dto.FinishRegistrationDto;
import edu.neoflex.model.dto.LoanOfferDto;
import edu.neoflex.repository.StatementRepository;
import edu.neoflex.utils.EntityDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculateByIdServiceImplTest {

    @Mock
    StatementRepository statementRepository;

    @Mock
    CalculatorApi calculatorApi;

    @Mock
    EntityDtoMapper mapper;

    @InjectMocks
    CalculateByIdServiceImpl calculateByIdService;

    private Statement statement;
    private Credit credit;
    private FinishRegistrationDto finishRegistrationDto;
    private UUID statementId = UUID.fromString("043b9717-b311-4810-b1b6-4fb2457dd3f1");

    @BeforeEach
    public void init() {

        statement = Statement.builder()
                .statementId(statementId)
                .appliedOffer(LoanOfferDto.builder()
                        .statementId(statementId)
                        .rate(BigDecimal.valueOf(7))
                        .monthlyPayment(BigDecimal.valueOf(17000))
                        .term(9)
                        .requestedAmount(BigDecimal.valueOf(100000))
                        .build())
                .statusHistory(new ArrayList<>())
                .build();

        credit = Credit.builder()
                .term(7)
                .psk(BigDecimal.valueOf(100000))
                .amount(BigDecimal.valueOf(95000))
                .creditStatus(CreditStatus.CALCULATED)
                .statement(statement)
                .paymentSchedule(new ArrayList<>())
                .creditId(UUID.randomUUID())
                .build();

        finishRegistrationDto = FinishRegistrationDto.builder()
                .accountNumber(UUID.randomUUID())
                .dependentAmount(0)
                .employment(EmploymentDto.builder().build())
                .maritalStatus(MaritalStatus.MARRIED)
                .passportIssueDate(LocalDate.of(2020, 12, 12))
                .passportIssueBranch("")
                .gender(Gender.MALE)
                .build();

    }


    @Test
    public void calculate(){
        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));
        when(calculatorApi.calculateCredit(any())).thenReturn(CreditDto.builder().build());
        when(mapper.getCredit(any(CreditDto.class))).thenReturn(credit);

        calculateByIdService.calculate(finishRegistrationDto, statementId);

        verify(statementRepository, times(1)).findById(statementId);
        verify(calculatorApi, times(1)).calculateCredit(any());
        verify(mapper, times(1)).getCredit(any());

    }

    @Test
    public void calculate_whenInvalidId_thenThrowAppEx(){
        assertThrows(AppException.class, () -> calculateByIdService.calculate(finishRegistrationDto, null));
    }

    @Test
    public void calculate_whenNull_thenThrowNPE(){
        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));
        assertThrows(NullPointerException.class, () -> calculateByIdService.calculate(null, statementId));

    }

}