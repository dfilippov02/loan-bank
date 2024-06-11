package edu.neoflex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(name = "Payment schedule element")
public class PaymentScheduleElementDto {
    @Schema(description = "The serial number of the payment")
    private Integer number;
    @Schema(description = "The payment date")
    private LocalDate date;
    @Schema(description = "Full period payment")
    private BigDecimal totalPayment;
    @Schema(description = "Percents payment amount")
    private BigDecimal interestPayment;
    @Schema(description = "Debt payment amount")
    private BigDecimal debtPayment;
    @Schema(description = "Remaining debt payment amount")
    private BigDecimal remainingDebt;
}
