package org.lastrix.mafp.kraken.web.v1.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lastrix.jwt.Jwt;
import org.lastrix.mafp.kraken.service.UserPositionService;
import org.lastrix.mafp.roles.client.api.RequireRoles;
import org.lastrix.mafp.tea.client.impl.TeaClientService;
import org.lastrix.mafp.tea.model.dto.TeaCupDto;
import org.lastrix.rest.Rest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tea")
public class TeaController {
    private final TeaClientService service;
    private final UserPositionService positionService;
    private final Jwt jwt;

    @RequireRoles("User")
    @PostMapping
    public Rest<TeaCupDto> drink(@RequestParam Integer amount, @RequestParam String teaType, @RequestParam @Size(min = 3, max = 128) String position) {
        UUID userId = jwt.getUserId();
        if (!positionService.userHasAccess(userId, position))
            throw new IllegalStateException("No access to position: " + position);
        return Rest.of(service.drink(userId, amount, teaType));
    }
}
