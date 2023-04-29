package edu.tartu.esi.mapper;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.model.ParkingSlot;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ParkingSlotMapper.class})
public interface ParkingSlotMapper extends EntityMapper<ParkingSlot, ParkingSlotDto> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ParkingSlotDto toDto(ParkingSlot entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ParkingSlot toEntity(ParkingSlotDto dto);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<ParkingSlot> toEntities(List<ParkingSlotDto> entityList);
}
