package edu.neoflex.domain.jsonb;

import edu.neoflex.domain.enums.EmploymentPosition;
import edu.neoflex.domain.enums.EmploymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class Employment {

    private UUID employment_uuid;

    private EmploymentStatus status;
    private String employmentInn;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;

}
