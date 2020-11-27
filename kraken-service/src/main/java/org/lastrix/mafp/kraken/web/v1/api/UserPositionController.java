package org.lastrix.mafp.kraken.web.v1.api;

import lombok.RequiredArgsConstructor;
import org.lastrix.jwt.Jwt;
import org.lastrix.mafp.kraken.model.dto.PositionDto;
import org.lastrix.mafp.kraken.persistence.mapper.PositionMapper;
import org.lastrix.mafp.kraken.service.UserPositionService;
import org.lastrix.mafp.roles.client.api.RequireAnyRole;
import org.lastrix.mafp.roles.client.api.RequireRoles;
import org.lastrix.rest.Rest;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/position/user")
public class UserPositionController {
    private final UserPositionService service;
    private final Jwt jwt;
    private final PositionMapper mapper;

    @RequireRoles("PositionManager")
    @PostMapping("/{userId}/positions")
    public Rest<Boolean> setUserPositions(@PathVariable UUID userId, @RequestParam @Size(min = 1, max = 512) Set<String> positions) {
        service.setUserPositions(userId, positions);
        return Rest.ok();
    }

    @RequireRoles("PositionManager")
    @PutMapping("/{userId}/positions")
    public Rest<Boolean> addUserRoles(@PathVariable UUID userId, @RequestParam @Size(min = 1, max = 512) Set<String> positions) {
        service.addUserPositions(userId, positions);
        return Rest.ok();
    }

    @RequireRoles("PositionManager")
    @DeleteMapping("/{userId}/positions")
    public Rest<Boolean> removeUserRoles(@PathVariable UUID userId, @RequestParam @Size(min = 1, max = 512) Set<String> positions) {
        service.removeUserPositions(userId, positions);
        return Rest.ok();
    }

    @RequireRoles("PositionManager")
    @GetMapping("/{userId}/positions")
    public Rest<PositionDto> getUserPositions(@PathVariable UUID userId) {
        return Rest.of(service.getUserPositions(userId), mapper);
    }

    @RequireAnyRole("User")
    @GetMapping("/positions")
    public Rest<PositionDto> getMyPositions() {
        return Rest.of(service.getUserPositions(jwt.getUserId()), mapper);
    }
}
