package edu.neoflex.controller;


import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.LoanOfferDto;
import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.exception.ControllerAdvice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface CalculatorController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan offers created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoanOfferDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Error during prescoring",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ControllerAdvice.ErrorDto.class)))}
    )
    @Operation(summary = "Get loan offers")
    List<LoanOfferDto> getOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequest);


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan calculated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreditDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Error during scoring",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ControllerAdvice.ErrorDto.class)))}
    )
    @Operation(summary = "Get loan with payment schedule")
    CreditDto getCredit(@RequestBody @Valid ScoringDataDto scoringData);
}
