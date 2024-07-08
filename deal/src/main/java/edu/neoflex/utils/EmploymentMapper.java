package edu.neoflex.utils;

import edu.neoflex.domain.jsonb.Employment;
import edu.neoflex.dto.EmploymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmploymentMapper {
    EmploymentMapper INSTANCE = Mappers.getMapper(EmploymentMapper.class);

    @Mapping(target = "employmentInn", source = "employerINN")
    @Mapping(target = "status", source = "employmentStatus")
    Employment toEntity(EmploymentDto employmentDto);
}
