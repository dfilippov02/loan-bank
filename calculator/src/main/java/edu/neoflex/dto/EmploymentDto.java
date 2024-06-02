package edu.neoflex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Builder
@Schema(name = "Employment")
public class EmploymentDto {

    @NotNull(message = "Null parameter - employmentStatus")
    private EmploymentStatus employmentStatus;

    @NotNull(message = "Null parameter - employerINN")
    @Pattern(regexp = "[\\d]{12}", message = "Wrong format employerINN")
    @Schema(description = "Employment position", example = "032456154123")
    private String employerINN;

    @NotNull(message = "Null parameter - salary")
    @Schema(description = "Salary", example = "40000.00")
    private BigDecimal salary;

    @NotNull(message = "Null parameter - position")
    private EmploymentPosition position;

    @NotNull(message = "Null parameter - workExperienceTotal")
    @Schema(description = "Current work experience in months", example = "20")
    private Integer workExperienceTotal;

    @NotNull(message = "Null parameter - workExperienceCurrent")
    @Schema(description = "Current work experience in months", example = "10")
    private Integer workExperienceCurrent;

    @Schema(name = "Employment status", description = "Issuer employment status", enumAsRef = true, example = "SELF_EMPLOYED")
    public enum EmploymentStatus{
        UNEMPLOYED,
        EMPLOYED,
        SELF_EMPLOYED,
        BUSINESSMAN
    }
    @Schema(name = "Employment position",description = "Issuer employment position",enumAsRef = true)
    public enum EmploymentPosition{
        MIDDLE_LEVEL_MANAGER,
        TOP_LEVEL_MANAGER,
    }
}
