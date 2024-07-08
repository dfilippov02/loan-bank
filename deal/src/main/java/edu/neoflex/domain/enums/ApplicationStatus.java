package edu.neoflex.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Application status", enumAsRef = true)
public enum ApplicationStatus {

    PREAPPROVAL,
    APPROVED,
    CC_DENIED,
    CC_APPROVED,
    PREPARE_DOCUMENTS,
    DOCUMENT_CREATED,
    CLIENT_DENIED,
    DOCUMENT_SIGNED,
    CREDIT_ISSUED
}
