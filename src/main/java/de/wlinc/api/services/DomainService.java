package de.wlinc.api.services;

import de.wlinc.api.entity.Domain;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface DomainService {

    Domain createDomain(Jwt jwt, Domain domain);

    List<Domain> getDomains(Jwt jwt);

    Domain getDomainById(Jwt jwt, Long id);

    Domain updateDomain(Jwt jwt, Long id, Domain domain);

    Domain deleteDomain(Jwt jwt, Long id);

    Domain updateDomainStatus(Jwt jwt, Long id, String status);
}
