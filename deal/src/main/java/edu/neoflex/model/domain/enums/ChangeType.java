package edu.neoflex.model.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Change type", enumAsRef = true)
public enum ChangeType {

    AUTOMATIC,
    MANUAL
}
