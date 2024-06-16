package edu.neoflex.model.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Employment status", description = "Issuer employment status", enumAsRef = true, example = "SELF_EMPLOYED")
public enum EmploymentStatus {

    UNEMPLOYED,
    SELF_EMPLOYED,
    EMPLOYED,
    BUSINESS_OWNER
}