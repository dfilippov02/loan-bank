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
public class LoanStatementRequestDto {

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

    @NotNull(message = "Null parameter - email")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Wrong format of email")
    private String email;

    @NotNull(message = "Null parameter - birthdate")
    @Past(message = "Birthdate must be in past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @NotNull(message = "Null parameter - passportSeries")
    @Pattern(regexp = "[\\d]{4}", message = "Wrong format of passportSeries")
    private String passportSeries;

    @NotNull(message = "Null parameter - passportNumber")
    @Pattern(regexp = "[\\d]{6}", message = "Wrong format of passportNumber")
    private String passportNumber;
}
