package org.lastrix.mafp.kraken.persistence.repository;

import org.lastrix.mafp.kraken.persistence.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    @Query("SELECT p FROM Position p JOIN UserPosition up ON up.id.positionId = p.id WHERE up.id.userId = ?1")
    List<Position> findAllForUser(UUID userId);

    @Query("SELECT p FROM Position p WHERE p.name IN (?1)")
    List<Position> findAllByNames(Collection<String> roles);

    @Query("SELECT p FROM Position p WHERE p.name = ?1")
    Optional<Position> findByName(String positionName);
}
