package edu.neoflex.utils;

import edu.neoflex.dto.LoanStatementRequestDto;

public interface PrescoringProcessor {

    void prescoring(LoanStatementRequestDto loanStatementRequest);

}
