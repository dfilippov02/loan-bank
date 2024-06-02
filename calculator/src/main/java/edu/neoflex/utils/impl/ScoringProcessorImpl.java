package edu.neoflex.utils.impl;

import edu.neoflex.dto.LoanStatementRequestDto;
import edu.neoflex.dto.ScoringDataDto;
import edu.neoflex.exception.AppPrescoringException;
import edu.neoflex.exception.AppScoringException;
import edu.neoflex.utils.ScoringProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@Slf4j
public class ScoringProcessorImpl implements ScoringProcessor {

    @Value("${loan.rate}")
    BigDecimal loanBaseRate;


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


    public BigDecimal scoring(ScoringDataDto scoringData) {
        log.info("Started scoring for {} {}, account number - {}",
                scoringData.getFirstName(),
                scoringData.getLastName(),
                scoringData.getAccountNumber());
        int rateDelta = 0;
        switch (scoringData.getEmployment().getEmploymentStatus()){
            case UNEMPLOYED -> throw new AppScoringException(new Throwable("Client is not employed"));
            case SELF_EMPLOYED -> rateDelta = rateDelta + 1;
            case BUSINESSMAN -> rateDelta = rateDelta + 2;
        }
        switch (scoringData.getEmployment().getPosition()){
            case TOP_LEVEL_MANAGER -> rateDelta = rateDelta - 3;
            case MIDDLE_LEVEL_MANAGER -> rateDelta = rateDelta - 2;
        }
        if(scoringData.getEmployment().getSalary().multiply(BigDecimal.valueOf(25)).compareTo(scoringData.getAmount()) < 0)
            throw new AppScoringException(new Throwable("Credit amount is more then 25 salaries"));
        switch (scoringData.getMaritalStatus()){
            case MARRIED -> rateDelta = rateDelta - 3;
            case DIVORCED -> rateDelta = rateDelta + 1;
        }
        int years = Period.between(scoringData.getBirthdate(), LocalDate.now()).getYears();
        if(years > 65 || years < 20)
            throw new AppScoringException(new Throwable("The age of the client is not between 20 and 65 years"));
        if(scoringData.getGender().equals(ScoringDataDto.Gender.FEMALE) && years >= 32 && years <= 60)
            rateDelta = rateDelta - 3;
        if(scoringData.getGender().equals(ScoringDataDto.Gender.MALE) && years >= 30 && years <= 55)
            rateDelta = rateDelta -3;
        if(scoringData.getGender().equals(ScoringDataDto.Gender.NON_BINARY))
            rateDelta = rateDelta + 7;
        if(scoringData.getEmployment().getWorkExperienceCurrent() < 3)
            throw new AppScoringException(new Throwable("The client's current experience is less than 3 months"));
        if(scoringData.getEmployment().getWorkExperienceTotal() < 18)
            throw new AppScoringException(new Throwable("The client's total work experience is less than 18 months"));

        BigDecimal loanRate = loanBaseRate.add(BigDecimal.valueOf(rateDelta));

        log.info("Finished scoring for {} {}, account number - {}. loan rate - {}",
                scoringData.getFirstName(),
                scoringData.getLastName(),
                scoringData.getAccountNumber(),
                loanRate);

        return loanRate;
    }
}
