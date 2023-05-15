package edu.tartu.esi;

import edu.tartu.esi.mapper.EntityMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AnalyticsMapper.class})
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
