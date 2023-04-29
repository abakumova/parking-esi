package edu.tartu.esi.mapper;

import edu.tartu.esi.dto.BookingDto;
import edu.tartu.esi.model.Booking;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BookingMapper.class})
public interface BookingMapper extends EntityMapper<Booking, BookingDto> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookingDto toDto(Booking entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Booking toEntity(BookingDto dto);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<Booking> toEntities(List<BookingDto> entityList);
}
