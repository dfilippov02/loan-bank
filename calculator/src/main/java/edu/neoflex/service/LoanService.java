package edu.neoflex.service;

import edu.neoflex.dto.CreditDto;
import edu.neoflex.dto.ScoringDataDto;

public interface LoanService {

    CreditDto calculateLoan(ScoringDataDto scoringData);
}
