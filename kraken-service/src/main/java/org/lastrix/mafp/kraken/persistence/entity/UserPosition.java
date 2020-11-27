package org.lastrix.mafp.kraken.persistence.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_position")
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserPosition {
    public UserPosition(UserPositionId id) {
        this.id = id;
        this.deleted = false;
    }

    @EmbeddedId
    private UserPositionId id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;
}
