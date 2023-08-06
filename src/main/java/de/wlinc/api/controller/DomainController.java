package de.wlinc.api.controller;

import de.wlinc.api.entity.Domain;
import de.wlinc.api.services.DomainService;
import de.wlinc.api.services.impl.DomainServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domain")
@RequiredArgsConstructor
public class DomainController {

    final DomainServiceImpl domainService;

    @Operation(summary = "Get domain by name", description = "Get domain by name, only accessible by an admin")
    @ApiResponse(responseCode = "200", description = "Found the domain")
    @ApiResponse(responseCode = "204", description = "Domain not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping
    public List<Domain> getDomains(@AuthenticationPrincipal Jwt jwt) {
        return domainService.getDomains(jwt);
    }

    @Operation(summary = "Get domain by id", description = "Get domain by id, only accessible by an admin")
    @ApiResponse(responseCode = "200", description = "Found the domain")
    @ApiResponse(responseCode = "204", description = "Domain not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping("/{id}")
    public Domain getDomainById(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        return domainService.getDomainById(jwt, id);
    }

    @Operation(summary = "Create a new domain", description = "Create a new domain, only accessible by an registered user")
    @ApiResponse(responseCode = "200", description = "Found the domain")
    @ApiResponse(responseCode = "204", description = "Domain not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @PostMapping
    public Domain createDomain(@AuthenticationPrincipal Jwt jwt, @RequestBody Domain domain) {
        return domainService.createDomain(jwt, domain);
    }

    @Operation(summary = "Update a domain", description = "Update a domain, only accessible by an admin or the owner")
    @ApiResponse(responseCode = "200", description = "Updated the domain")
    @ApiResponse(responseCode = "204", description = "Domain not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @PutMapping("/{id}")
    public Domain updateDomain(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody Domain domain) {
        return domainService.updateDomain(jwt, id, domain);
    }

    @Operation(summary = "Delete a domain", description = "Delete a domain, only accessible by an admin or the owner")
    @ApiResponse(responseCode = "200", description = "Deleted the domain")
    @ApiResponse(responseCode = "204", description = "Domain not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @DeleteMapping("/{id}")
    public Domain deleteDomain(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        return domainService.deleteDomain(jwt, id);
    }

    @Operation(summary = "Update a domain status", description = "Update a domain status, only accessible by an admin or the owner")
    @ApiResponse(responseCode = "200", description = "Updated the domain status")
    @ApiResponse(responseCode = "204", description = "Domain not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @ApiResponse(responseCode = "403", description = "No Access", content = @io.swagger.v3.oas.annotations.media.Content)
    @PutMapping("/{id}/status")
    public Domain updateDomainStatus(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @Param("status") String status) {
        return domainService.updateDomainStatus(jwt, id, status);
    }
}
