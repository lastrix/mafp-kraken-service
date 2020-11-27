package org.lastrix.mafp.kraken.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserPositionId implements Serializable {
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;
    @Column(name = "position_id", nullable = false, updatable = false)
    private Integer positionId;
}
