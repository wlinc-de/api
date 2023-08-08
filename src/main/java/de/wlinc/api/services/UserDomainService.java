package de.wlinc.api.services;

import de.wlinc.api.entity.UserDomain;
import org.springframework.security.oauth2.jwt.Jwt;


public interface UserDomainService {

    UserDomain createUserDomain(Jwt jwt, String user, String domainIds);

    UserDomain deleteUserDomain(Jwt jwt, String user, Long domainId);

    UserDomain getUserDomainsByUser(Jwt jwt, String user);

}
