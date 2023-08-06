package de.wlinc.api.services.impl;

import de.wlinc.api.def.DomainStatus;
import de.wlinc.api.entity.Domain;
import de.wlinc.api.repositories.DomainRepository;
import de.wlinc.api.services.DomainService;
import de.wlinc.api.util.PermissionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl implements DomainService {

    final DomainRepository domainRepository;
    final PermissionChecker permissionChecker;
    @Override
    public Domain createDomain(Jwt jwt, Domain domain) {
        permissionChecker.hasUserOneOfRoles(jwt, new String[]{"add_domain"});

        var existingDomain = domainRepository.getDomainByDomain(domain.getDomain());
        if (existingDomain != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Domain already exists");
        }

        Domain newDomain = new Domain();
        newDomain.setDomain(domain.getDomain());
        newDomain.setLinkedBy(jwt.getSubject());
        newDomain.setActive(false);
        newDomain.setStatus(DomainStatus.PENDING);

        return domainRepository.save(newDomain);
    }

    @Override
    public List<Domain> getDomains(Jwt jwt) {
        permissionChecker.hasUserOneOfRoles(jwt, new String[]{"read_domain"});
        var existingDomain = domainRepository.getDomainsByLinkedBy(jwt.getSubject());
        if (existingDomain != null) {
            return existingDomain;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Domain not found");
        }
    }

    @Override
    public Domain getDomainById(Jwt jwt, Long id) {
        var existingDomain = domainRepository.findById(id);
        if(existingDomain.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Ether domain not found or you don't have permission to access it.");
        }
        permissionChecker.isUserSelfOrAdmin(jwt, existingDomain.get().getLinkedBy());
        return existingDomain.get();
    }

    @Override
    public Domain updateDomain(Jwt jwt, Long id, Domain domain) {
        var existingDomain = domainRepository.findById(id);
        if(existingDomain.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Ether domain not found or you don't have permission to access it.");
        }
        permissionChecker.isUserSelfOrAdmin(jwt, existingDomain.get().getLinkedBy());
        existingDomain.get().setDomain(domain.getDomain());
        existingDomain.get().setActive(domain.isActive());
        existingDomain.get().setStatus(domain.getStatus());
        return domainRepository.save(existingDomain.get());
    }

    @Override
    public Domain deleteDomain(Jwt jwt, Long id) {
        var existingDomain = domainRepository.findById(id);
        if(existingDomain.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Ether domain not found or you don't have permission to access it.");
        }
        permissionChecker.isUserSelfOrAdmin(jwt, existingDomain.get().getLinkedBy());
        domainRepository.delete(existingDomain.get());
        return existingDomain.get();
    }

    @Override
    public Domain updateDomainStatus(Jwt jwt, Long id, String status) {
        var existingDomain = domainRepository.findById(id);
        if(existingDomain.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Ether domain not found or you don't have permission to access it.");
        }
        permissionChecker.isUserSelfOrAdmin(jwt, existingDomain.get().getLinkedBy());
        existingDomain.get().setStatus(DomainStatus.valueOf(status));
        return domainRepository.save(existingDomain.get());
    }
}
