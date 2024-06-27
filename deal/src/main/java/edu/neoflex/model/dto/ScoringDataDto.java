package edu.neoflex.model.dto;

import edu.neoflex.model.domain.enums.Gender;
import edu.neoflex.model.domain.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(name = "Scoring data", description = "dto for scoring and loan calculation")
public class ScoringDataDto {

    @NotNull(message = "Null parameter - amount")
    @Min(value = 30000, message = "amount is less then 30000")
    @Schema(description = "Requested amount")
    private BigDecimal amount;

    @NotNull(message = "Null parameter - term")
    @Min(value = 6, message = "minimal term is 6 months")
    @Schema(description = "Period in months")
    private Integer term;

    @NotNull(message = "Null parameter - fistName")
    @Pattern(regexp = "[a-zA-Z ]{2,30}", message = "Wrong format of firstName")
    @Schema(description = "Applicant firstname", example = "Daniel")
    private String firstName;

    @NotNull(message = "Null parameter - lastName")
    @Pattern(regexp = "[a-zA-Z ]{2,30}", message = "Wrong format of lastName")
    @Schema(description = "Applicant lastname", example = "Rudkovsky")
    private String lastName;

    @Pattern(regexp = "[a-zA-Z ]{2,30}", message = "Wrong format of middleName")
    @Schema(description = "Applicant middlename if exists", example = "Ivanov")
    private String middleName;

    @NotNull(message = "Null parameter - gender")
    private Gender gender;

    @NotNull(message = "Null parameter - birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Birthdate must be in past")
    @Schema(description = "Applicant birthdate (format YYYY-MM-DD)", example = "2002-12-12")
    private LocalDate birthdate;

    @NotNull(message = "Null parameter - passportSeries")
    @Pattern(regexp = "[\\d]{4}", message = "Wrong format of passportSeries")
    @Schema(description = "Passport series", example = "0000")
    private String passportSeries;

    @NotNull(message = "Null parameter - passportNumber")
    @Pattern(regexp = "[\\d]{6}", message = "Wrong format of passportNumber")
    @Schema(description = "Passport number", example = "000000")
    private String passportNumber;

    @NotNull(message = "Null parameter - passportIssueDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "PassportIssueDate must be in path")
    @Schema(description = "Date of issue of the passport", example = "2020-12-12")
    private LocalDate passportIssueDate;

    @NotNull(message = "Null parameter - passportIssueBranch")
    @Schema(description = "Passport control department")
    private String passportIssueBranch;

    @NotNull(message = "Null parameter - maritalStatus")
    private MaritalStatus maritalStatus;

    @NotNull(message = "Null parameter - dependentAmount")
    @Schema(description = "Dependent persons amount")
    private Integer dependentAmount;

    @NotNull(message = "Null parameter - employment")
    private EmploymentDto employment;

    @NotNull(message = "Null parameter - accountNumber")
    private String accountNumber;

    @NotNull(message = "Null parameter - isInsuranceEnabled")
    @Schema(description = "Is insurance enabled?")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "Null parameter - isSalaryClient")
    @Schema(description = "Is salary client?")
    private Boolean isSalaryClient;

}

