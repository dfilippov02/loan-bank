package edu.neoflex.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Builder
public class EmploymentDto {

    @NotNull(message = "Null parameter - employmentStatus")
    private EmploymentStatus employmentStatus;

    @NotNull(message = "Null parameter - employerINN")
    @Pattern(regexp = "[\\d]{12}", message = "Неверный формат employerINN")
    private String employerINN;

    @NotNull(message = "Null parameter - salary")
    private BigDecimal salary;

    @NotNull(message = "Null parameter - position")
    private EmploymentPosition position;

    @NotNull(message = "Null parameter - workExperienceTotal")
    private Integer workExperienceTotal;

    @NotNull(message = "Null parameter - workExperienceCurrent")
    private Integer workExperienceCurrent;

    public enum EmploymentStatus{
        UNEMPLOYED,
        EMPLOYED,
        SELF_EMPLOYED,
        BUSINESSMAN
    }

    public enum EmploymentPosition{
        MIDDLE_LEVEL_MANAGER,
        TOP_LEVEL_MANAGER,
    }
}
