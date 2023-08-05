package de.wlinc.api.services;

import de.wlinc.api.entity.Link;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LinkService {
    List<Link> getAllLinks(Jwt jwt);
    List<Link> getLinksByUser(Jwt jwt, String user);

    Link getLinkByToken(Jwt jwt, String token);

    Link getLinkById(Jwt jwt, Long id);
    Link createLink(Jwt jwt, Link link);
    Link updateLink(Jwt jwt, Link link);
    void deleteLink(Jwt jwt, Long id);
}
