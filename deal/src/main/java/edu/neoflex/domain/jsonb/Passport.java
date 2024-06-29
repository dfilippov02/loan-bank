package edu.neoflex.domain.jsonb;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class Passport {

    private UUID passport_uuid;

    @NotNull(message = "Null parameter - passportSeries")
    @Pattern(regexp = "[\\d]{4}", message = "Wrong format of passportSeries")
    @Schema(description = "Passport series", example = "0000")
    private String series;

    @NotNull(message = "Null parameter - passportNumber")
    @Pattern(regexp = "[\\d]{6}", message = "Wrong format of passportNumber")
    @Schema(description = "Passport number", example = "000000")
    private String number;

    @NotNull(message = "Null parameter - passportIssueDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "PassportIssueDate must be in path")
    @Schema(description = "Date of issue of the passport", example = "2020-12-12")
    private LocalDate issueDate;

    @NotNull(message = "Null parameter - passportIssueBranch")
    @Schema(description = "Passport control department")
    private String issueBranch;
}
