package org.lastrix.mafp.kraken.persistence.mapper;

import org.lastrix.mafp.kraken.model.dto.PositionDto;
import org.lastrix.mafp.kraken.persistence.entity.Position;
import org.lastrix.rest.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PositionMapper implements EntityMapper<Position, PositionDto> {
}
