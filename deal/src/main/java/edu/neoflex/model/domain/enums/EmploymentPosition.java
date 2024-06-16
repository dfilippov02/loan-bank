package edu.neoflex.model.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Employment position", description = "Issuer employment position", enumAsRef = true)
public enum EmploymentPosition {

    WORKER("WORKER"),
    MIDDLE_LEVEL_MANAGER("MID_MANAGER"),
    TOP_LEVEL_MANAGER("TOP_MANAGER"),
    OWNER("OWNER");

    private final String dbname;

    EmploymentPosition(String dbname) {
        this.dbname = dbname;
    }

    @Override
    public String toString() {
        return this.dbname;
    }
}
