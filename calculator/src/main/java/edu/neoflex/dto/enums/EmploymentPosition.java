package edu.neoflex.dto.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Employment position", description = "Issuer employment position", enumAsRef = true)
public enum EmploymentPosition {
    MIDDLE_LEVEL_MANAGER,
    TOP_LEVEL_MANAGER,
}
