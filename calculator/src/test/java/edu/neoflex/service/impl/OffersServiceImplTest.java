package edu.neoflex.service.impl;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.utils.LoanParamsCalculator;
import edu.neoflex.utils.ScoringProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OffersServiceImplTest {

    @Mock
    ScoringProcessor scoringProcessor;

    @Mock
    LoanParamsCalculator loanParamsCalculator;

    @InjectMocks
    OffersServiceImpl offersService;

    LoanStatementRequestDto loanStatementRequestDto;
    List<LoanOfferDto> offersExpected = new ArrayList<>();


    @BeforeEach
    private void init() {
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
        when(loanParamsCalculator.countInsuranceCost(any())).thenReturn(BigDecimal.ZERO);
        when(loanParamsCalculator.countLoanRate(any(), any()))
                .thenReturn(BigDecimal.valueOf(7))
                .thenReturn(BigDecimal.valueOf(8))
                .thenReturn(BigDecimal.valueOf(9))
                .thenReturn(BigDecimal.valueOf(10));
        when(loanParamsCalculator.countInsuranceCost(any())).thenReturn(BigDecimal.valueOf(2000));
        when(loanParamsCalculator.countMonthPayment(any(), any(), anyInt())).thenReturn(BigDecimal.valueOf(17000));
        when(loanParamsCalculator.countTotalAmount(any(), anyInt())).thenReturn(BigDecimal.valueOf(104000));


        List<LoanOfferDto> offers = offersService.createOffers(loanStatementRequestDto);
        offers.forEach(o -> o.setStatementId(null));
        assertEquals(offers.size(), 4);
        assertEquals(offersExpected, offers);
        assertEquals(offersExpected, offersExpected.stream().sorted(Comparator.comparing(LoanOfferDto::getRate).reversed()).toList());
    }

    @Test
    void getOffers_whenNullThenThrowNPE() {
        assertThrows(NullPointerException.class, () -> offersService.createOffers(null));
    }
}