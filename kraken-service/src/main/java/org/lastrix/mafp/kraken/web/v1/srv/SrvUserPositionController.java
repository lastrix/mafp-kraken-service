package org.lastrix.mafp.kraken.web.v1.srv;

import lombok.RequiredArgsConstructor;
import org.lastrix.jwt.UserType;
import org.lastrix.mafp.kraken.persistence.entity.Position;
import org.lastrix.mafp.kraken.service.UserPositionService;
import org.lastrix.mafp.roles.client.api.RequireRoles;
import org.lastrix.rest.Rest;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/srv/v1/position/user")
public class SrvUserPositionController {
    private final UserPositionService service;

    @RequireRoles(value = "ServiceUser", userTypes = UserType.SRV)
    @GetMapping("/{userId}/positions")
    public Rest<String> getUserPositions(@PathVariable UUID userId) {
        return Rest.of(service.getUserPositions(userId).stream().map(Position::getName).collect(Collectors.toList()));
    }

    @RequireRoles(value = "ServiceUser", userTypes = UserType.SRV)
    @GetMapping("/{userId}/check")
    public Rest<Boolean> hasPositions(@PathVariable UUID userId, @RequestParam @Size(min = 1, max = 512) List<String> positions) {
        return Rest.of(!positions.isEmpty() && service.hasPositions(userId, positions));
    }

    @RequireRoles(value = "ServiceUser", userTypes = UserType.SRV)
    @GetMapping("/{userId}/check/any")
    public Rest<Boolean> hasAnyPosition(@PathVariable UUID userId, @RequestParam @Size(min = 1, max = 512) List<String> positions) {
        return Rest.of(!positions.isEmpty() && service.hasAnyPosition(userId, positions));
    }
}
