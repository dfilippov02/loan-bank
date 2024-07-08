package edu.neoflex.utils;


import edu.neoflex.domain.Client;
import edu.neoflex.domain.Statement;
import edu.neoflex.dto.ScoringDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientToScoringDataMapper {
    ClientToScoringDataMapper INSTANCE = Mappers.getMapper(ClientToScoringDataMapper.class);

    @Mapping(source= "passport.series",                  target  = "passportSeries")
    @Mapping(source= "passport.number",                  target  = "passportNumber")
    @Mapping(source= "passport.issueDate",               target  = "passportIssueDate")
    @Mapping(source= "passport.issueBranch",             target  = "passportIssueBranch")
    @Mapping(source= "employment.position",              target  = "employment.position")
    @Mapping(source= "employment.salary",                target  = "employment.salary")
    @Mapping(source= "employment.employmentInn",         target  = "employment.employerINN")
    @Mapping(source= "employment.status",                target  = "employment.employmentStatus")
    @Mapping(source= "employment.workExperienceCurrent", target  = "employment.workExperienceCurrent")
    ScoringDataDto clientToScoringDataDto(Client client);

    default ScoringDataDto map(Client client, Statement statement){
        ScoringDataDto scoringDataDto = clientToScoringDataDto(client);
        scoringDataDto.setAmount(statement.getAppliedOffer().getRequestedAmount());
        scoringDataDto.setTerm(statement.getAppliedOffer().getTerm());
        scoringDataDto.setIsSalaryClient(statement.getAppliedOffer().getIsSalaryClient());
        scoringDataDto.setIsInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled());

        return scoringDataDto;
    }
}
