package de.wlinc.api.services;

import de.wlinc.api.entity.Link;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface LinkService {
    public List<Link> getAllLinks(Jwt jwt);
    public List<Link> getLinksByUser(Jwt jwt, String user);

    public Link getLinkByToken(Jwt jwt, String token);

    public Link getLinkById(Jwt jwt, Long id);
    public Link createLink(Jwt jwt, Link link);
    public Link updateLink(Jwt jwt, Link link);
    public void deleteLink(Jwt jwt, Long id);
}
