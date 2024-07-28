package edu.neoflex.controller;

import edu.neoflex.dto.FinishRegistrationDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface GatewayApi {
    @Operation(summary = "Get loan offers")
    ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequest);

    @Operation(summary = "Select offer")
    void selectOffers(@RequestBody @Valid LoanOfferDto loanOfferDto);

    @Operation(summary = "Get loan with payment schedule")
    void calculateCredit(@RequestBody @Valid FinishRegistrationDto finishRegistrationDto, @PathVariable UUID statementId);

    @Operation(summary = "Send documents by statement id")
    void send(@PathVariable UUID statementId);

    @Operation(summary = "Send code for signing")
    void sign(@PathVariable UUID statementId);

    @Operation(summary = "Sign documents")
    void code(@PathVariable UUID statementId, @RequestParam String code);
}
