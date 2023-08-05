package de.wlinc.api.services.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import de.wlinc.api.entity.File;
import de.wlinc.api.entity.Link;
import de.wlinc.api.repositories.FileRepository;
import de.wlinc.api.services.FileService;
import de.wlinc.api.util.PermissionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    final FileRepository fileRepository;
    final PermissionChecker permissionChecker;
    final LinkServiceImpl linkService;

    @Value("${wlinc.upload.path}")
    private String uploadPath = "uploads";

    @Value("${wlinc.upload.view.path}")
    private String viewPath;

    @Override
    public File uploadFile(Jwt jwt, MultipartFile multipartFile) {
        permissionChecker.hasUserOneOfRoles(jwt, new String[]{"upload_file"});

        if(multipartFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        File file = new File();
        file.setUser(jwt.getSubject());
        file.setMime_type(multipartFile.getContentType());
        file.setOriginal_name(multipartFile.getOriginalFilename());
        file.setSize(multipartFile.getSize());
        file.setUploaded_at(LocalDateTime.now());

        var uuid = UUID.randomUUID();
        Path destination = Path.of(uploadPath, uuid.toString()).toAbsolutePath();

        file.setUUID(uuid.toString());
        file.setPath(destination.toString());

        handleUpload(multipartFile, destination);


        try {
            ByteSource byteSource = com.google.common.io.Files.asByteSource(destination.toFile());
            HashCode hc = byteSource.hash(Hashing.sha256());
            file.setHash(hc.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Link link = new Link();
        link.setType("file");
        link.setUrl(link.getDomain() + viewPath + "/" + file.getHash());

        var savedLink = linkService.createLink(jwt, link);
        file.setLink(savedLink);

        return fileRepository.save(file);
    }

    @Override
    public File getFileByHash(Jwt jwt, String hashName) {
        var file = fileRepository.getFileByHash(hashName);
        if(file.isPresent()) {
            permissionChecker.isUserSelfOrAdmin(jwt, file.get().getUser());
            return file.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Ether the file is not found or you have no access to it.");
        }
    }


    @Override
    public List<File> getFilesByUser(Jwt jwt, String user) {
        permissionChecker.isUserSelfOrAdmin(jwt, user);
        return fileRepository.getFilesByUser(user);
    }

    @Override
    public File getFileByLinkToken(String token) {
        var file = fileRepository.getFileByLinkToken(token);
        if(file.isPresent()) {
            return file.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Ether the file is not found or you have no access to it.");
        }
    }

    @Override
    public void deleteFile(Jwt jwt, Long id) {
        var file = fileRepository.findById(id);
        if(file.isPresent()) {
            permissionChecker.isUserSelfOrAdmin(jwt, file.get().getUser());
            fileRepository.delete(file.get());
            linkService.deleteLink(jwt, file.get().getLink().getId());
        }

    }
    private void handleUpload(MultipartFile file, Path destination) {
        try {
            Files.createDirectories(destination.getParent());
            Files.copy(file.getInputStream(), destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
