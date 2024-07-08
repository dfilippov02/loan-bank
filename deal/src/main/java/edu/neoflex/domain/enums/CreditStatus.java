package edu.neoflex.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Credit status", enumAsRef = true)
public enum CreditStatus {

    CALCULATED,
    ISSUED
}
