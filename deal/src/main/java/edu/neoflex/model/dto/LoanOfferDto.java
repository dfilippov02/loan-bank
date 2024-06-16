package edu.neoflex.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Schema(name = "Loan offer")
public class LoanOfferDto {

    @Schema(description = "Unique identifier")
    private UUID statementId;
    @Schema(description = "Requested amount", example = "100000")
    private BigDecimal requestedAmount;
    @Schema(description = "Total amount", example = "105000")
    private BigDecimal totalAmount;
    @Schema(description = "Period in months", example = "6")
    @Min(value = 6, message = "term is less than 6")
    private Integer term;
    @Schema(description = "Monthly payment", example = "17000.00")
    private BigDecimal monthlyPayment;
    @Schema(description = "Credit rate", example = "7")
    private BigDecimal rate;
    @Schema(description = "Is insurance enabled?")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Is salary client?")
    private Boolean isSalaryClient;
}
