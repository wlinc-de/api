package de.wlinc.api.services;



import de.wlinc.api.entity.File;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    File uploadFile(Jwt jwt, MultipartFile multipartFile) throws IOException;

    File getFileByHash(Jwt jwt, String hashName);

    List<File> getFilesByUser(Jwt jwt, String user);

    File getFileByLinkToken(String token);

    void deleteFile(Jwt jwt, Long id);
}
