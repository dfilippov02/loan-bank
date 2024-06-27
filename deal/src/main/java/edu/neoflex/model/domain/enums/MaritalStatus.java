package edu.neoflex.model.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Marital status", description = "Applicant marital status", enumAsRef = true)
public enum MaritalStatus {
    MARRIED,
    DIVORCED,
    SINGLE,
    WIDOW_WIDOWER
}
