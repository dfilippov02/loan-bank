package edu.neoflex.utils;

import edu.neoflex.domain.Credit;
import edu.neoflex.dto.CreditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreditMapper {
    CreditMapper INSTANCE = Mappers.getMapper(CreditMapper.class);

    @Mapping(target = "insuranceEnabled", source = "isInsuranceEnabled")
    Credit toEntity(CreditDto creditDto);

    @Mapping(target = "isInsuranceEnabled", source = "insuranceEnabled")
    CreditDto toDto(Credit credit);


}
