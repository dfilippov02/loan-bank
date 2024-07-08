package edu.neoflex.api;

import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Deal", url = "${feign.client.server}")
public interface DealApi {

    @PostMapping("/statement")
    List<LoanOfferDto> getOffers(@RequestBody LoanStatementRequestDto requestDto);

    @PostMapping("/offer/select")
    void selectOffers(@RequestBody LoanOfferDto offerDto);

}
