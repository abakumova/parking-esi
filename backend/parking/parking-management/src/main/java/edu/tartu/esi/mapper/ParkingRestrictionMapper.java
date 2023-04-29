package edu.tartu.esi.mapper;

import edu.tartu.esi.dto.ParkingRestrictionDto;
import edu.tartu.esi.model.ParkingRestriction;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ParkingRestrictionMapper.class})
public interface ParkingRestrictionMapper extends EntityMapper<ParkingRestriction, ParkingRestrictionDto> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ParkingRestrictionDto toDto(ParkingRestriction entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ParkingRestriction toEntity(ParkingRestrictionDto dto);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<ParkingRestriction> toEntities(List<ParkingRestrictionDto> entityList);
}
