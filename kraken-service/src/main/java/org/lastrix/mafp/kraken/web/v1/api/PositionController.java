package org.lastrix.mafp.kraken.web.v1.api;

import lombok.RequiredArgsConstructor;
import org.lastrix.mafp.kraken.model.dto.PositionDto;
import org.lastrix.mafp.kraken.persistence.mapper.PositionMapper;
import org.lastrix.mafp.kraken.service.PositionService;
import org.lastrix.mafp.roles.client.api.RequireRoles;
import org.lastrix.rest.Pagination;
import org.lastrix.rest.Rest;
import org.lastrix.rest.ValidGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/position")
public class PositionController {
    private final PositionService service;
    private final PositionMapper mapper;

    @RequireRoles("PositionManager")
    @PostMapping
    public Rest<PositionDto> create(@RequestBody @Validated(ValidGroup.Create.class) PositionDto position) {
        return Rest.of(service.create(mapper.fromDto(position)), mapper);
    }

    @RequireRoles("PositionManager")
    @PutMapping
    public Rest<PositionDto> update(@RequestBody @Validated(ValidGroup.Create.class) PositionDto position) {
        return Rest.of(service.update(mapper.fromDto(position)), mapper);
    }

    @RequireRoles("PositionManager")
    @GetMapping("/{positionName}")
    public Rest<PositionDto> getRole(@PathVariable String positionName) {
        return Rest.of(service.getByName(positionName), mapper);
    }

    @RequireRoles("PositionManager")
    @GetMapping("/page")
    public Rest<PositionDto> page(@ModelAttribute @Validated(ValidGroup.Read.class) Pagination pagination) {
        return Rest.of(service.page(pagination),
                pagination,
                mapper
        );
    }

    @RequireRoles("PositionManager")
    @PostMapping("/page")
    public Rest<PositionDto> pagePost(@RequestBody @Validated(ValidGroup.Read.class) Pagination pagination) {
        return Rest.of(service.page(pagination),
                pagination,
                mapper
        );
    }
}
