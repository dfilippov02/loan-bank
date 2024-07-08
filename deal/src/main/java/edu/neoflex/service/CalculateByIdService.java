package edu.neoflex.service;

import edu.neoflex.dto.FinishRegistrationDto;

import java.util.UUID;

public interface CalculateByIdService {

    void calculate(FinishRegistrationDto finishRegistrationDto, UUID statementId);

}
