package edu.tartu.esi.mapper;

import edu.tartu.esi.Analytics;
import edu.tartu.esi.AnalyticsDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalyticsMapper extends EntityMapper<Analytics, AnalyticsDto> {
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnalyticsDto toDto(Analytics entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Analytics toEntity(AnalyticsDto dto);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<Analytics> toEntities(List<AnalyticsDto> entityList);
}
