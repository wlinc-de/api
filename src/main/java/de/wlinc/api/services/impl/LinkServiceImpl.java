package de.wlinc.api.services.impl;

import de.wlinc.api.entity.Link;
import de.wlinc.api.repositories.LinkRepository;
import de.wlinc.api.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    final LinkRepository linkRepository;

    @Override
    public List<Link> getAllLinks(Jwt jwt) {
        return (List<Link>) linkRepository.findAll();
    }

    @Override
    public List<Link> getLinksByUser(Jwt jwt, String user) {
        return List.of(new Link[0]);
    }

    @Override
    public Link getLinkByToken(Jwt jwt, String token) {
        var link = linkRepository.getLinkByToken(token);
        if (link != null) {
            return link;
        }else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Link not found");
        }
    }

    @Override
    public Link getLinkById(Jwt jwt, Long id) {
        return null;
    }

    @Override
    public Link createLink(Jwt jwt, Link link) {
        return linkRepository.save(link);
    }


    @Override
    public Link updateLink(Jwt jwt, Link link) {
        return null;
    }

    @Override
    public void deleteLink(Jwt jwt, Long id) {

    }
}
