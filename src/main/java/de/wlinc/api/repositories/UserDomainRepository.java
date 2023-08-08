package de.wlinc.api.repositories;

import de.wlinc.api.entity.UserDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDomainRepository extends CrudRepository<UserDomain, Long> {
    Optional<UserDomain> getUserDomainsByUser(String user);
}
