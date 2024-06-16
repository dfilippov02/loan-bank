package edu.neoflex.model.dto;

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
@Schema(name = "Loan statement request", description = "dto for prescoring and offers generation")
public class LoanStatementRequestDto {

    @NotNull(message = "Null parameter - amount")
    @Min(value = 30000, message = "amount is less then 30000")
    @Schema(description = "Requested amount")
    private BigDecimal amount;

    @NotNull(message = "Null parameter - term")
    @Min(value = 6, message = "Minimal term is 6 months")
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

    @NotNull(message = "Null parameter - email")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Wrong format of email")
    @Schema(description = "Applicant email", example = "testtest@test.ru")
    private String email;

    @NotNull(message = "Null parameter - birthdate")
    @Past(message = "Birthdate must be in past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
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
}
