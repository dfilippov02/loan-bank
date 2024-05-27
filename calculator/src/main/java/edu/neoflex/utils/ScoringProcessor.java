package edu.neoflex.utils;

import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;

import java.math.BigDecimal;

public interface ScoringProcessor {

    void prescoring(LoanStatementRequestDto loanStatementRequest);

    BigDecimal scoring(ScoringDataDto scoringData);


}
