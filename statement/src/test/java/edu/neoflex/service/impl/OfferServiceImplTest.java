package edu.neoflex.service.impl;

import edu.neoflex.api.DealApi;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.utils.impl.PrescoringProcessorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @Mock
    DealApi dealApi;

    @Mock
    PrescoringProcessorImpl prescoringProcessor;

    @InjectMocks
    StatementServiceImpl statementService;

    LoanStatementRequestDto loanStatementRequestDto;
    List<LoanOfferDto> offersExpected = new ArrayList<>();

    private UUID id = UUID.fromString("043b9717-b311-4810-b1b6-4fb2457dd3f1");

    @BeforeEach
    private void init() {

        loanStatementRequestDto = LoanStatementRequestDto.builder().build();

        loanStatementRequestDto.setBirthdate(LocalDate.of(2002, 12,12));
        loanStatementRequestDto.setEmail("test@mail.ru");
        loanStatementRequestDto.setTerm(6);
        loanStatementRequestDto.setAmount(BigDecimal.valueOf(100000));

        offersExpected.add(LoanOfferDto.builder()
                .statementId(id)
                .rate(BigDecimal.valueOf(10))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .monthlyPayment(BigDecimal.valueOf(10000))
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(105000))
                .build());
        offersExpected.add(LoanOfferDto.builder()
                .statementId(id)
                .rate(BigDecimal.valueOf(9))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build());
        offersExpected.add(LoanOfferDto.builder()
                .statementId(id)
                .rate(BigDecimal.valueOf(8))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build());
        offersExpected.add(LoanOfferDto.builder()
                .statementId(id)
                .rate(BigDecimal.valueOf(7))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build());

        offersExpected.forEach(o -> {
            o.setTotalAmount(BigDecimal.valueOf(104000));
            o.setMonthlyPayment(BigDecimal.valueOf(17000));
            o.setRequestedAmount(loanStatementRequestDto.getAmount());
            o.setTerm(loanStatementRequestDto.getTerm());
        });
    }

    @Test
    void createOffers() {

        when(dealApi.getOffers(any())).thenReturn(offersExpected);

        List<LoanOfferDto> offers = statementService.getOffers(loanStatementRequestDto);

        assertTrue(offers.stream().filter(o -> o.getStatementId()!=id).findAny().isEmpty());
        assertEquals(offers.get(0).getIsInsuranceEnabled(), offersExpected.get(0).getIsInsuranceEnabled());
        assertEquals(offers.get(0).getIsSalaryClient(), offersExpected.get(0).getIsSalaryClient());
        assertEquals(offers.get(0).getTerm(), offersExpected.get(0).getTerm());
        assertEquals(offers.get(0).getRate(), offersExpected.get(0).getRate());
        assertEquals(offers.get(0).getStatementId(), offersExpected.get(0).getStatementId());
        assertEquals(offers.get(0).getRequestedAmount(), offersExpected.get(0).getRequestedAmount());
        assertEquals(offers.get(0).getTotalAmount(), offersExpected.get(0).getTotalAmount());
        assertEquals(offers.get(0).getMonthlyPayment(), offersExpected.get(0).getMonthlyPayment());
        assertEquals(offers.size(), 4);
        assertEquals(offers, offers.stream().sorted(Comparator.comparing(LoanOfferDto::getRate).reversed()).toList());
    }

    @Test
    void createOffers_whenNullThenThrowNPE() {
        assertThrows(NullPointerException.class, () -> statementService.getOffers(null));

    }

    @Test
    void updateStatement(){

        LoanOfferDto loanOfferDto = LoanOfferDto.builder().statementId(id).build();
        statementService.selectOffer(loanOfferDto);

        verify(dealApi, times(1)).selectOffers(loanOfferDto);
    }


    @Test
    void updateStatement_whenNull_ThenThrowNPE(){
        assertThrows(NullPointerException.class, () -> statementService.selectOffer(null));
    }

}