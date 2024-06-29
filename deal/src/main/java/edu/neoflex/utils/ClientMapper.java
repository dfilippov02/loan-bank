package edu.neoflex.utils;

import edu.neoflex.domain.Client;
import edu.neoflex.dto.LoanStatementRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(source = "passportSeries", target = "passport.series")
    @Mapping(source = "passportNumber", target = "passport.number")
    Client toEntity(LoanStatementRequestDto loanStatementRequestDto);
}
