package edu.neoflex.model.domain.jsonb;

import edu.neoflex.model.domain.enums.ApplicationStatus;
import edu.neoflex.model.domain.enums.ChangeType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class StatusHistory {

    private ApplicationStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
