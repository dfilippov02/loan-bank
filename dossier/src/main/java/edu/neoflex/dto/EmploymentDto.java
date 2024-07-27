package edu.neoflex.dto;

import edu.neoflex.dto.enums.EmploymentPosition;
import edu.neoflex.dto.enums.EmploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
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

}
