package edu.neoflex.controller;

import edu.neoflex.domain.Statement;
import edu.neoflex.dto.FinishRegistrationDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.exception.ControllerAdvice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface DealApi {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan offers created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoanOfferDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Error during prescoring",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ControllerAdvice.ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Error during requesting calculator component",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ControllerAdvice.ErrorDto.class)))}
    )
    @Operation(summary = "Get loan offers")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody @Valid LoanStatementRequestDto requestDto);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan offer selected",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error during select offer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ControllerAdvice.ErrorDto.class)))}
    )
    @Operation(summary = "Get loan offers")
    public void selectOffers(@RequestBody @Valid LoanOfferDto offerDto);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan calculated",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Error during calculating",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ControllerAdvice.ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Error during requesting calculator component",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ControllerAdvice.ErrorDto.class)))}
    )
    @Operation(summary = "Get loan with payment schedule")
    public void calculateCredit(@RequestBody @Valid FinishRegistrationDto finishRegistrationDto, @PathVariable UUID statementId);

    @Operation(summary = "Send documents by statement id")
    void send(@PathVariable UUID statementId);

    @Operation(summary = "Send code for signing")
    void sign(@PathVariable UUID statementId);

    @Operation(summary = "Sign documents")
    void code(@PathVariable UUID statementId, @RequestParam String code);

    @Operation(summary = "Get statement by id")
    ResponseEntity<Statement> getStatement(@PathVariable UUID statementId);

    @Operation(summary = "Get all statements")
    ResponseEntity<List<Statement>> getStatementList();
}
