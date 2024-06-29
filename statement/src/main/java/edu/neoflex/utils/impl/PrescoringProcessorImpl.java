package edu.neoflex.utils.impl;

import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.exception.AppPrescoringException;
import edu.neoflex.utils.PrescoringProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@Slf4j
public class PrescoringProcessorImpl implements PrescoringProcessor {


    public void prescoring(LoanStatementRequestDto loanStatementRequest) {
        log.info("Started prescoring for {} {}",
                loanStatementRequest.getFirstName(),
                loanStatementRequest.getLastName());
        if (Period.between(loanStatementRequest.getBirthdate(), LocalDate.now()).getYears() < 18) {
            throw new AppPrescoringException(new Throwable("Age less then 18 years"));
        }
        log.info("Finished prescoring for {} {}",
                loanStatementRequest.getFirstName(),
                loanStatementRequest.getLastName());
    }
}
