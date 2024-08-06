package edu.neoflex.dto;

import edu.neoflex.dto.enums.Gender;
import edu.neoflex.dto.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Schema(name = "Finish registration data")
public class FinishRegistrationDto {

        @NotNull(message = "Null parameter - gender")
        Gender gender;

        @NotNull(message = "Null parameter - maritalStatus")
        MaritalStatus maritalStatus;

        @NotNull(message = "Null parameter - dependentAmount")
        @Min(value = 0, message = "dependentAmount must be 0 or more")
        @Schema(description = "Dependent persons amount")
        Integer dependentAmount;


        @NotNull(message = "Null parameter - passportIssueDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @Past(message = "PassportIssueDate must be in past")
        @Schema(description = "Date of issue of the passport", example = "2020-12-12")
        private LocalDate passportIssueDate;

        @NotNull(message = "Null parameter - passportIssueBranch")
        @Schema(description = "Passport control department")
        private String passportIssueBranch;

        @NotNull(message = "Null parameter - employment")
        EmploymentDto employment;

        @NotNull(message = "Null parameter - account number")
        UUID accountNumber;
}
