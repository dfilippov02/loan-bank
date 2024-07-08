package edu.neoflex.domain.jsonb;

import edu.neoflex.domain.enums.ApplicationStatus;
import edu.neoflex.domain.enums.ChangeType;
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
