package edu.neoflex.utils;

import edu.neoflex.dto.ScoringDataDto;

import java.math.BigDecimal;

public interface ScoringProcessor {

    BigDecimal scoring(ScoringDataDto scoringData);


}
