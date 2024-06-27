package edu.neoflex.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(name = "Credit")
public class CreditDto {
    @Schema(name = "Requested amount")
    private BigDecimal amount;
    @Schema(description = "Period in months")
    private Integer term;
    @Schema(description = "Monthly payment")
    private BigDecimal monthlyPayment;
    @Schema(description = "Credit rate")
    private BigDecimal rate;
    @Schema(description = "Full credit payment")
    private BigDecimal psk;
    @Schema(description = "Is insurance enabled?")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Is salary client?")
    private Boolean isSalaryClient;
    @Schema(description = "Payment schedule")
    private List<PaymentScheduleElementDto> paymentSchedule;
}
