package edu.neoflex.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class EmailMessageDto {
        private String address;
        private EmailMessageTheme theme;
        private UUID statementId;
        private String sesCode;
        private ScoringDataDto scoringDataDto;
        private CreditDto creditDto;
}
