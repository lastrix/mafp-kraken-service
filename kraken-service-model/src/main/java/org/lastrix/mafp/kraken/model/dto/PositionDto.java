package org.lastrix.mafp.kraken.model.dto;

import lombok.*;
import org.lastrix.rest.ValidGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PositionDto {
    @NotNull
    @Size(min = 3, max = 128)
    private String name;

    @NotNull(groups = ValidGroup.Create.class)
    @Size(max = 2048)
    private String description;
}
