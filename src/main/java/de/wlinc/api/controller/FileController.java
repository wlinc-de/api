package de.wlinc.api.controller;

import de.wlinc.api.entity.File;
import de.wlinc.api.services.impl.FileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    final FileServiceImpl fileService;

    @Operation(summary = "Upload a file", description = "Upload a file, only accessible by an registered user")
    @ApiResponse(responseCode = "200", description = "Created the file")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @PostMapping
    public File uploadFile(@AuthenticationPrincipal Jwt jwt, @RequestBody MultipartFile file) {
        return fileService.uploadFile(jwt, file);
    }

    @Operation(summary = "Get file by hash", description = "Get a file by its hash, only accessible by the user or an admin")
    @ApiResponse(responseCode = "200", description = "Found the file")
    @ApiResponse(responseCode = "204", description = "File not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping("/{hashName}")
    public File getFileByHash(@AuthenticationPrincipal Jwt jwt, @PathVariable String hashName) {
        return fileService.getFileByHash(jwt, hashName);
    }

    @Operation(summary = "Get file by user", description = "Get a file by its user, only accessible by the user or an admin")
    @ApiResponse(responseCode = "200", description = "Found the files")
    @ApiResponse(responseCode = "204", description = "File not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @GetMapping("/{user}")
    public List<File> getFilesByUser(@AuthenticationPrincipal Jwt jwt, @PathVariable String user) {
        return fileService.getFilesByUser(jwt, user);
    }

    @Operation(summary = "Get file by link token", description = "Get a file by its link token, only accessible by anyone")
    @ApiResponse(responseCode = "200", description = "Found the file")
    @ApiResponse(responseCode = "204", description = "File not found")
    @GetMapping
    public File getFileByLinkToken(@Param("token") String token) {
        return fileService.getFileByLinkToken(token);
    }

    @Operation(summary = "Delete file", description = "Delete a file, only accessible by the user or an admin")
    @ApiResponse(responseCode = "200", description = "Deleted the file")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
    @DeleteMapping("/{id}")
    public void deleteFile(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        fileService.deleteFile(jwt, id);
    }
}
