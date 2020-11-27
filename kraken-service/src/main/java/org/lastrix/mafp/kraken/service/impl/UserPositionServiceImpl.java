package org.lastrix.mafp.kraken.service.impl;

import lombok.RequiredArgsConstructor;
import org.lastrix.mafp.kraken.persistence.entity.Position;
import org.lastrix.mafp.kraken.persistence.entity.UserPosition;
import org.lastrix.mafp.kraken.persistence.entity.UserPositionId;
import org.lastrix.mafp.kraken.persistence.repository.PositionRepository;
import org.lastrix.mafp.kraken.persistence.repository.UserPositionRepository;
import org.lastrix.mafp.kraken.service.PositionService;
import org.lastrix.mafp.kraken.service.UserPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserPositionServiceImpl implements UserPositionService {
    private final UserPositionRepository repository;
    private final PositionRepository positionRepository;
    private final PositionService positionService;

    @Transactional
    @Override
    public void setUserPositions(UUID userId, Set<String> positions) {
        var userPositions = repository.findAllByUserId(userId);
        var map = positionService.findPositions(positions);
        enableEach(userId, map.values());
        var all = new HashSet<>(map.values());
        userPositions.stream()
                .filter(e -> !all.contains(e))
                .forEach(e -> repository.save(new UserPosition(new UserPositionId(userId, e), true)));
    }

    @Transactional
    @Override
    public void addUserPositions(UUID userId, Set<String> positions) {
        var map = positionService.findPositions(positions);
        enableEach(userId, map.values());
    }

    @Transactional
    @Override
    public void removeUserPositions(UUID userId, Set<String> positions) {
        var map = positionService.findPositions(positions);
        map.forEach((k, v) -> markDeletedIfExists(userId, v));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Position> getUserPositions(UUID userId) {
        return positionRepository.findAllForUser(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean userHasAccess(UUID userId, String position) {
        return repository.hasAccessTo(userId, position);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasPositions(UUID userId, List<String> positions) {
        if (positions.isEmpty()) {
            return false;
        }
        if (positions.size() == 1) {
            return repository.hasAccessTo(userId, positions.iterator().next());
        }
        var r = positionService.findPositions(positions);
        return repository.countUserPositions(userId, r.values()) == positions.size();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasAnyPosition(UUID userId, List<String> positions) {
        if (positions.isEmpty()) {
            return false;
        }
        if (positions.size() == 1) {
            return repository.hasAccessTo(userId, positions.iterator().next());
        }
        var r = positionService.findPositions(positions);
        return repository.hasAnyPosition(userId, r.values());
    }

    private void markDeletedIfExists(UUID userId, Integer positionId) {
        var id = new UserPositionId(userId, positionId);
        repository.findById(id)
                .ifPresent(e -> {
                    e.setDeleted(true);
                    repository.save(e);
                });
    }

    private void enableEach(UUID userId, Collection<Integer> positionIds) {
        positionIds.forEach(positionId -> enablePosition(userId, positionId));
    }

    private void enablePosition(UUID userId, Integer positionId) {
        var id = new UserPositionId(userId, positionId);
        repository.findById(id).ifPresentOrElse(
                e -> {
                    if (Boolean.TRUE.equals(e.getDeleted())) {
                        e.setDeleted(false);
                        repository.save(e);
                    }
                },
                () -> repository.save(new UserPosition(id))
        );
    }
}
