package edu.neoflex.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class EmailMessageDto {
        private String address;
        private EmailMessageTheme theme;
        private UUID statementId;
        private String sesCode;
        private ScoringDataDto scoringDataDto;
        private CreditDto creditDto;
}
