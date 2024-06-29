package edu.neoflex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Schema(name = "Loan offer")
public class LoanOfferDto {

    @Schema(description = "Unique identifier")
    private UUID statementId;
    @Schema(description = "Requested amount")
    private BigDecimal requestedAmount;
    @Schema(description = "Total amount")
    private BigDecimal totalAmount;
    @Schema(description = "Period in months")
    private Integer term;
    @Schema(description = "Monthly payment")
    private BigDecimal monthlyPayment;
    @Schema(description = "Credit rate")
    private BigDecimal rate;
    @Schema(description = "Is insurance enabled?")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Is salary client?")
    private Boolean isSalaryClient;
}
