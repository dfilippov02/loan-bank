package edu.neoflex.service.impl;

import edu.neoflex.clientApi.CalculatorApi;
import edu.neoflex.exception.AppException;
import edu.neoflex.model.domain.Client;
import edu.neoflex.model.domain.Statement;
import edu.neoflex.model.dto.LoanOfferDto;
import edu.neoflex.model.dto.LoanStatementRequestDto;
import edu.neoflex.repository.ClientRepository;
import edu.neoflex.repository.StatementRepository;
import edu.neoflex.utils.EntityDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private StatementRepository statementRepository;
    @Mock
    private CalculatorApi calculatorApi;
    @Mock
    private EntityDtoMapper mapper;

    @InjectMocks
    OfferServiceImpl offerService;


    LoanStatementRequestDto loanStatementRequestDto;
    List<LoanOfferDto> offersExpected = new ArrayList<>();
    Statement statement;

    private UUID id = UUID.fromString("043b9717-b311-4810-b1b6-4fb2457dd3f1");

    @BeforeEach
    private void init() {

        statement = Statement.builder()
                .statusHistory(new ArrayList<>())
                .build();

        loanStatementRequestDto = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(6)
                .build();

        offersExpected.add(LoanOfferDto.builder()
                .rate(BigDecimal.valueOf(10))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build());
        offersExpected.add(LoanOfferDto.builder()
                .rate(BigDecimal.valueOf(9))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build());
        offersExpected.add(LoanOfferDto.builder()
                .rate(BigDecimal.valueOf(8))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build());
        offersExpected.add(LoanOfferDto.builder()
                .rate(BigDecimal.valueOf(7))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build());

        offersExpected.forEach(o -> {
            o.setTotalAmount(BigDecimal.valueOf(104000));
            o.setMonthlyPayment(BigDecimal.valueOf(17000));
            o.setRequestedAmount(BigDecimal.valueOf(100000));
            o.setTerm(6);
        });
    }

    @Test
    void createOffers() {

        when(mapper.getClientByDto(loanStatementRequestDto)).thenReturn(new Client());
        when(clientRepository.save(any())).thenReturn(new Client());
        when(statementRepository.save(any())).thenReturn(Statement.builder()
                .statementId(id)
                .build());

        when(calculatorApi.getOffers(any())).thenReturn(offersExpected);

        List<LoanOfferDto> offers = offerService.getOffers(loanStatementRequestDto);

        assertTrue(offers.stream().filter(o -> o.getStatementId()!=id).findAny().isEmpty());
        assertEquals(offers.size(), 4);
        assertEquals(offers, offers.stream().sorted(Comparator.comparing(LoanOfferDto::getRate).reversed()).toList());

        verify(clientRepository, times(1)).save(any());
        verify(statementRepository, times(1)).save(any());
        verify(calculatorApi, times(1)).getOffers(any());
    }

    @Test
    void createOffers_whenNullThenThrowNPE() {
        assertThrows(NullPointerException.class, () -> offerService.getOffers(null));

    }

    @Test
    void updateStatement(){

        when(statementRepository.findById(id)).thenReturn(Optional.ofNullable(statement));

        offerService.selectOffer(LoanOfferDto.builder()
                .statementId(id)
                .build());

        verify(statementRepository, times(1)).findById(id);
    }


    @Test
    void updateStatement_whenInvalidId_thenThrowAppEx(){
        assertThrows(AppException.class, () -> offerService.selectOffer(LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .build()));
    }

    @Test
    void updateStatement_whenNull_ThenThrowNPE(){
        assertThrows(NullPointerException.class, () -> offerService.selectOffer(null));
    }

}