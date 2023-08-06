package de.wlinc.api.repositories;

import de.wlinc.api.entity.Domain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomainRepository extends CrudRepository<Domain, Long> {
    Domain getDomainByDomain(String domainName);
    List<Domain> getDomainsByLinkedBy(String linkedBy);
}
