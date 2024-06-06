package edu.neoflex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neoflex.controller.impl.CalculatorControllerImpl;
import edu.neoflex.dto.*;
import edu.neoflex.dto.enums.Gender;
import edu.neoflex.dto.enums.MaritalStatus;
import edu.neoflex.service.LoanService;
import edu.neoflex.service.OffersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorControllerImpl.class)
class CalculatorControllerImplTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    OffersService offersService;
    @MockBean
    LoanService loanService;
    @Mock
    Validator mockValidator;

    @Test
    void getOffers() throws Exception {
        List<LoanOfferDto> offers = new ArrayList<>();
        offers.add(LoanOfferDto.builder()
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(102000))
                .term(20)
                .monthlyPayment(BigDecimal.valueOf(5050))
                .rate(BigDecimal.valueOf(10))
                .isSalaryClient(false)
                .isInsuranceEnabled(false)
                .build());
        when(offersService.createOffers(any())).thenReturn(offers);
        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LoanStatementRequestDto.builder()
                                .firstName("Daniel")
                                .lastName("Rudkovsky")
                                .passportNumber("111111")
                                .passportSeries("1111")
                                .birthdate(LocalDate.of(1990, 12, 12))
                                .amount(BigDecimal.valueOf(100000))
                                .email("dRudkovsky@test.ru")
                                .term(6)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requestedAmount").value(100000))
                .andExpect(jsonPath("$[0].term").value(20))
                .andExpect(jsonPath("$[0].rate").value(10))
                .andExpect(jsonPath("$[0].monthlyPayment").value(5050))
                .andExpect(jsonPath("$[0].totalAmount").value(102000));
    }

    @Test
    void getCredit() throws Exception {

        when(loanService.calculateLoan(any())).thenReturn(CreditDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .monthlyPayment(BigDecimal.valueOf(5050))
                .psk(BigDecimal.valueOf(102000))
                .term(20)
                .rate(BigDecimal.valueOf(10))
                .paymentSchedule(new ArrayList<>())
                .build());

        mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ScoringDataDto.builder()
                                .term(20)
                                .isInsuranceEnabled(false)
                                .isSalaryClient(false)
                                .employment(EmploymentDto.builder().build())
                                .dependentAmount(0)
                                .passportIssueDate(LocalDate.of(2020, 12, 12))
                                .firstName("Daniel")
                                .lastName("Rudkovsky")
                                .accountNumber("123123")
                                .passportNumber("111111")
                                .passportSeries("1111")
                                .passportIssueBranch("123")
                                .birthdate(LocalDate.of(1990, 12, 12))
                                .amount(BigDecimal.valueOf(100000))
                                .gender(Gender.MALE)
                                .maritalStatus(MaritalStatus.MARRIED)
                                .build())))
                .andExpect(jsonPath("$.amount").value(100000))
                .andExpect(jsonPath("$.term").value(20))
                .andExpect(jsonPath("$.rate").value(10))
                .andExpect(jsonPath("$.monthlyPayment").value(5050))
                .andExpect(jsonPath("$.psk").value(102000));

    }
}