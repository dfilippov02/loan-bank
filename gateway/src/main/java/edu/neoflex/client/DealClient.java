package edu.neoflex.client;

import edu.neoflex.dto.FinishRegistrationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

@FeignClient(name = "Deal", url = "${feign.deal.server}")
public interface DealClient {

    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@RequestBody @Valid FinishRegistrationDto finishRegistrationDto, @PathVariable UUID statementId);

    @PostMapping("/document/{statementId}/send")
    void send(@PathVariable UUID statementId);

    @PostMapping("/document/{statementId}/sign")
    void sign(@PathVariable UUID statementId);

    @PostMapping("/document/{statementId}/code")
    void code(@PathVariable UUID statementId, @RequestParam String code);
}
