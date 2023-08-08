package de.wlinc.api.controller;

import de.wlinc.api.entity.UserDomain;
import de.wlinc.api.services.impl.UserDomainServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/domain/user")
@RequiredArgsConstructor
public class UserDomainController {

    final UserDomainServiceImpl userDomainService;

    @Operation(summary = "Create a new user domain", description = "Only admins any self can create domains for user")
    @ApiResponse(responseCode = "200", description = "User domain created")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @PostMapping
    public UserDomain createUserDomain(@AuthenticationPrincipal Jwt jwt, @RequestParam(value = "user", required = false) String user, @RequestParam( value = "domains") String domainIds) {
        return userDomainService.createUserDomain(jwt, user, domainIds);
    }

    @Operation(summary = "Delete a user domain", description = "Only admins any self can delete domains for user")
    @ApiResponse(responseCode = "200", description = "User domain deleted")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @DeleteMapping
    public UserDomain deleteUserDomain(@AuthenticationPrincipal Jwt jwt, @RequestParam(value = "user", required = false) String user, @RequestParam(value = "domain") Long domainId) {
        return userDomainService.deleteUserDomain(jwt, user, domainId);
    }

    @Operation(summary = "Get user domains for a specific user", description = "Only admins any self can get domains for user")
    @ApiResponse(responseCode = "200", description = "Found the user domains")
    @ApiResponse(responseCode = "204", description = "No user domains found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping
    public UserDomain getUserDomainsByUser(@AuthenticationPrincipal Jwt jwt, @RequestParam(value = "user", required = false) String user) {
        return userDomainService.getUserDomainsByUser(jwt, user);
    }
}
