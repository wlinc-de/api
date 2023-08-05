package de.wlinc.api.repositories;

import de.wlinc.api.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {
    Optional<File> getFileByHash(String hash);
    List<File> getFilesByUser(String user);
    Optional<File> getFileByLinkToken(String token);
}
