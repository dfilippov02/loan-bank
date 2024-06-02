package edu.neoflex.dto;

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
public class ScoringDataDto {

    @NotNull(message = "Null parameter - amount")
    @Min(value = 30000, message = "amount is less then 30000")
    private BigDecimal amount;

    @NotNull(message = "Null parameter - term")
    @Min(value = 6, message = "minimal term is 6 months")
    private Integer term;

    @NotNull(message = "Null parameter - fistName")
    @Pattern(regexp = "[a-zA-Z ]{2,30}", message = "Wrong format of firstName")
    private String firstName;

    @NotNull(message = "Null parameter - lastName")
    @Pattern(regexp = "[a-zA-Z ]{2,30}", message = "Wrong format of lastName")
    private String lastName;

    @Pattern(regexp = "[a-zA-Z ]{2,30}", message = "Wrong format of middleName")
    private String middleName;

    @NotNull(message = "Null parameter - gender")
    private Gender gender;

    @NotNull(message = "Null parameter - birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Birthdate must be in past")
    private LocalDate birthdate;

    @NotNull(message = "Null parameter - passportSeries")
    @Pattern(regexp = "[\\d]{4}", message = "Wrong format of passportSeries")
    private String passportSeries;

    @NotNull(message = "Null parameter - passportNumber")
    @Pattern(regexp = "[\\d]{6}", message = "Wrong format of passportNumber")
    private String passportNumber;

    @NotNull(message = "Null parameter - passportIssueDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate passportIssueDate;

    @NotNull(message = "Null parameter - passportIssueBranch")
    private String passportIssueBranch;

    @NotNull(message = "Null parameter - maritalStatus")
    private ScoringDataDto.MaritalStatus maritalStatus;

    @NotNull(message = "Null parameter - dependentAmount")
    private Integer dependentAmount;

    @NotNull(message = "Null parameter - employment")
    private EmploymentDto employment;

    @NotNull(message = "Null parameter - accountNumber")
    private String accountNumber;

    @NotNull(message = "Null parameter - isInsuranceEnabled")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "Null parameter - isSalaryClient")
    private Boolean isSalaryClient;

    public enum Gender {
        MALE,
        FEMALE,
        NON_BINARY
    }

    public enum MaritalStatus {
        MARRIED,
        DIVORCED,
        SINGLE
    }
}

