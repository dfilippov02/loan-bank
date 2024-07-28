package edu.neoflex.client;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "Statement", url = "${feign.statement.server}")
public interface StatementClient {

    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody @Valid LoanStatementRequestDto requestDto);

    @PostMapping("/offer")
    public void selectOffers(@RequestBody @Valid LoanOfferDto offerDto);

}
