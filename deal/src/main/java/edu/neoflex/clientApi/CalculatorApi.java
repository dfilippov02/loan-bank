package edu.neoflex.clientApi;

import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Calculator", url = "${feign.client.server}")
public interface CalculatorApi {

    @PostMapping("/calc")
    CreditDto calculateCredit(@RequestBody ScoringDataDto scoringDataDto);

    @PostMapping("/offers")
    List<LoanOfferDto> getOffers(@RequestBody LoanStatementRequestDto requestDto);
}
