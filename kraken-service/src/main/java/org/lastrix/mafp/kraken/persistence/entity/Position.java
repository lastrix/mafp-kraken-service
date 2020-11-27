package org.lastrix.mafp.kraken.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "position")
@Data
@ToString
@EqualsAndHashCode
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_position_id_auto_gen")
    @SequenceGenerator(name = "position_position_id_auto_gen", sequenceName = "position_seq", allocationSize = 1)
    @Column(name = "position_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
}
