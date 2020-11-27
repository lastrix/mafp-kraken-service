package org.lastrix.mafp.kraken.service;

import org.lastrix.mafp.kraken.persistence.entity.Position;
import org.lastrix.rest.Pagination;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Map;

public interface PositionService {
    Map<String, Integer> findPositions(Collection<String> names);

    Position create(Position position);

    Position update(Position position);

    Page<Position> page(Pagination pagination);

    Position getByName(String positionName);
}
