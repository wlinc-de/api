package de.wlinc.api.repositories;

import de.wlinc.api.entity.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    List<Link> getLinksByUser(String user);
    Optional<Link> getLinkByToken(String token);
}
