package de.wlinc.api.services.impl;

import de.wlinc.api.entity.Domain;
import de.wlinc.api.entity.UserDomain;
import de.wlinc.api.repositories.UserDomainRepository;
import de.wlinc.api.services.UserDomainService;
import de.wlinc.api.util.PermissionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDomainServiceImpl implements UserDomainService {

    final UserDomainRepository userDomainRepository;
    final PermissionChecker permissionChecker;
    final DomainServiceImpl domainService;
    @Override
    public UserDomain createUserDomain(Jwt jwt, String user, String domainIds) {
        user = user != null ? user : jwt.getSubject();
        permissionChecker.isUserSelfOrAdmin(jwt, user);
        var existingUserDomain = userDomainRepository.getUserDomainsByUser(user);
        System.out.println("existingUserDomain: " + existingUserDomain);

        var ids = domainIds.split(",");
        List<Domain> domains = new java.util.ArrayList<>();
        Arrays.stream(ids).map(Long::parseLong).forEach(id -> {
            var domain = domainService.getDomainById(jwt, id);
            System.out.println("domain: " + domain.getDomain());
            domains.add(domain);
        });
        if(existingUserDomain.isEmpty()){
            var userDomain = new UserDomain();
            userDomain.setUser(user != null ? user : jwt.getSubject());
            userDomain.setDomains(domains);
            return userDomainRepository.save(userDomain);

        }else {
            var userDomain = existingUserDomain.get();
            userDomain.setDomains(domains);
            return userDomainRepository.save(userDomain);
        }

    }

    @Override
    public UserDomain deleteUserDomain(Jwt jwt, String user, Long domainId) {
        user = user != null ? user : jwt.getSubject();
        permissionChecker.isUserSelfOrAdmin(jwt, user);
        var userDomain = userDomainRepository.getUserDomainsByUser(user);
        if(userDomain.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User has no domains assigned");
        }
        var domains = userDomain.get().getDomains();
        if(domains.size() == 1){
            userDomainRepository.delete(userDomain.get());
            return userDomain.get();
        }
        domains.forEach(domain -> {
            if(domain.getId().equals(domainId)){
                domains.remove(domain);
            }
        });
        userDomain.get().setDomains(domains);


        return userDomainRepository.save(userDomain.get());
    }

    @Override
    public UserDomain getUserDomainsByUser(Jwt jwt, String user) {
        user = user != null ? user : jwt.getSubject();
        permissionChecker.isUserSelfOrAdmin(jwt, user);
        var userDomains = userDomainRepository.getUserDomainsByUser(user);
        if(userDomains.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User has no domains assigned");
        }
        return userDomains.get();
    }

}
