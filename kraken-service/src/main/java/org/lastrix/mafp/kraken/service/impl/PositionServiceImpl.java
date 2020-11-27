package org.lastrix.mafp.kraken.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lastrix.mafp.kraken.persistence.entity.Position;
import org.lastrix.mafp.kraken.persistence.repository.PositionRepository;
import org.lastrix.mafp.kraken.service.PositionService;
import org.lastrix.rest.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Map<String, Integer> findPositions(Collection<String> roles) {
        roles = new HashSet<>(roles);
        if (roles.isEmpty()) return Collections.emptyMap();
        var list = repository.findAllByNames(roles);
        var map = list.stream().collect(Collectors.toMap(Position::getName, Position::getId));
        if (list.size() != roles.size())
            throw new IllegalArgumentException("Some positions are not exist: " + roles.stream().filter(e -> !map.containsKey(e)).collect(Collectors.joining(", ")));
        return map;
    }

    @Transactional
    @Override
    public Position create(Position position) {
        var r = new Position();
        r.setName(position.getName());
        r.setDescription(position.getDescription());
        return repository.save(r);
    }

    @Transactional
    @Override
    public Position update(Position position) {
        var r = getByName(position.getName());
        if (position.getDescription() != null) {
            r.setDescription(position.getDescription());
        }
        return repository.save(r);
    }


    @Transactional(readOnly = true)
    @Override
    public Position getByName(String roleName) {
        return repository.findByName(roleName).orElseThrow(() -> new IllegalArgumentException("No position: " + roleName));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Position> page(Pagination pagination) {
        return repository.findAll(pagination.toPageable(Sort.by("name")));
    }
}
