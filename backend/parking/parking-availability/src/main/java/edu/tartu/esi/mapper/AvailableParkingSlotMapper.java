package edu.tartu.esi.mapper;

import edu.tartu.esi.dto.AvailableParkingSlotDto;
import edu.tartu.esi.model.AvailableParkingSlot;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AvailableParkingSlotMapper.class})
public interface AvailableParkingSlotMapper extends EntityMapper<AvailableParkingSlot, AvailableParkingSlotDto> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AvailableParkingSlotDto toDto(AvailableParkingSlot entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AvailableParkingSlot toEntity(AvailableParkingSlotDto dto);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<AvailableParkingSlot> toEntities(List<AvailableParkingSlotDto> entityList);
}
