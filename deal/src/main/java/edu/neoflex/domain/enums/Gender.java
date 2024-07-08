package edu.neoflex.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Gender", description = "Applicant gender", enumAsRef = true)
public enum Gender {
    MALE,
    FEMALE,
    NON_BINARY
}
