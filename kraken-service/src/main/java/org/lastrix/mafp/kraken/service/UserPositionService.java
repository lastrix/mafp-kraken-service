package org.lastrix.mafp.kraken.service;

import org.lastrix.mafp.kraken.persistence.entity.Position;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserPositionService {
    void setUserPositions(UUID userId, Set<String> positions);

    void addUserPositions(UUID userId, Set<String> positions);

    void removeUserPositions(UUID userId, Set<String> positions);

    List<Position> getUserPositions(UUID userId);

    boolean userHasAccess(UUID userId, String position);

    boolean hasPositions(UUID userId, List<String> positions);

    boolean hasAnyPosition(UUID userId, List<String> positions);
}
