package de.wlinc.api.controller;

import de.wlinc.api.entity.Link;
import de.wlinc.api.services.impl.LinkServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/link")
@RequiredArgsConstructor
public class LinkController {

    final LinkServiceImpl linkService;

    @GetMapping("/all")
    public List<Link> getLinks(@AuthenticationPrincipal Jwt jwt) {
        return linkService.getAllLinks(jwt);
    }

    @PostMapping
    public Link createLink(@AuthenticationPrincipal Jwt jwt, Link link) {
        return linkService.createLink(jwt, link);
    }

    @Operation(summary = "Get link by token")
    @ApiResponse(responseCode = "200", description = "Found the link")
    @ApiResponse(responseCode = "204", description = "Link not found")
    @GetMapping
    public Link getLinkByToken(@AuthenticationPrincipal Jwt jwt,@Param(value = "token") String token) {
        return linkService.getLinkByToken(jwt, token);
    }

}
