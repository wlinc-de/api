package de.wlinc.api.controller;

import de.wlinc.api.entity.Link;
import de.wlinc.api.services.impl.LinkServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Get all links", description = "Get all links, only accessible by an admin")
    @ApiResponse(responseCode = "200", description = "Found the links")
    @ApiResponse(responseCode = "204", description = "No links found")
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping("/all")
    public List<Link> getLinks(@AuthenticationPrincipal Jwt jwt) {
        return linkService.getAllLinks(jwt);
    }

    @Operation(summary = "Create a shorted link", description = "Create a shorted link, only accessible by an registered user")
    @ApiResponse(responseCode = "200", description = "Created the link")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @PostMapping
    public Link createLink(@AuthenticationPrincipal Jwt jwt, @RequestBody Link link) {
        return linkService.createLink(jwt, link);
    }

    @Operation(summary = "Get link by token")
    @ApiResponse(responseCode = "200", description = "Found the link")
    @ApiResponse(responseCode = "204", description = "Link not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping
    public Link getLinkByToken(@AuthenticationPrincipal Jwt jwt, @Param(value = "token") String token) {
        return linkService.getLinkByToken(jwt, token);
    }

    @Operation(summary = "Get link by user", description = "Get all links of a user, only accessible by the user or an admin")
    @ApiResponse(responseCode = "200", description = "Found the link")
    @ApiResponse(responseCode = "204", description = "Link not found", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping("/{userId}")
    public List<Link> getLinksByUser(@AuthenticationPrincipal Jwt jwt, @PathVariable String userId) {
        return linkService.getLinksByUser(jwt, userId);
    }

    @Operation(summary = "Get link by id", description = "Get a link by its id, only accessible by the user or an admin")
    @ApiResponse(responseCode = "200", description = "Found the link")
    @ApiResponse(responseCode = "204", description = "Link not found", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping("/{id}")
    public Link getLinkById(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        return linkService.getLinkById(jwt, id);
    }

    @Operation(summary = "Edit a link", description = "Edit a link, only accessible by the user or an admin")
    @ApiResponse(responseCode = "200", description = "Edited the link")
    @ApiResponse(responseCode = "204", description = "Link not found or no access", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @PutMapping
    public Link updateLink(@AuthenticationPrincipal Jwt jwt, @RequestBody Link link) {
        return linkService.updateLink(jwt, link);
    }

    @Operation(summary = "Delete a link", description = "Delete a link, only accessible by the user or an admin")
    @ApiResponse(responseCode = "200", description = "Deleted the link")
    @ApiResponse(responseCode = "204", description = "Link not found or no access", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @DeleteMapping("/{id}")
    public void deleteLink(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        linkService.deleteLink(jwt, id);
    }

}

