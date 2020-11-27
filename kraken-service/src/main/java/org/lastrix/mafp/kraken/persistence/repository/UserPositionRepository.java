package org.lastrix.mafp.kraken.persistence.repository;

import org.lastrix.mafp.kraken.persistence.entity.UserPosition;
import org.lastrix.mafp.kraken.persistence.entity.UserPositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserPositionRepository extends JpaRepository<UserPosition, UserPositionId> {

    @Query(value = "SELECT EXISTS(SELECT 1 FROM kraken_service.user_position up JOIN kraken_service.position p ON p.position_id = up.position_id WHERE p.name = ?2 AND up.user_id = ?1)", nativeQuery = true)
    boolean hasAccessTo(UUID userId, String position);

    @Query("SELECT p.id FROM Position p JOIN UserPosition up ON up.id.positionId = p.id WHERE up.id.userId = ?1")
    List<Integer> findAllByUserId(UUID userId);

    @Query("SELECT count(up) FROM UserPosition up WHERE up.id.userId = ?1 AND up.id.positionId IN (?2) AND up.deleted = false")
    int countUserPositions(UUID userId, Collection<Integer> values);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM kraken_service.user_position up WHERE up.user_id = ?1 AND up.position_id IN (?2) AND up.is_deleted = false)", nativeQuery = true)
    boolean hasAnyPosition(UUID userId, Collection<Integer> values);

}
