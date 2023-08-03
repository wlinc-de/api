package de.wlinc.api.services.impl;

import de.wlinc.api.entity.Link;
import de.wlinc.api.repositories.LinkRepository;
import de.wlinc.api.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    LinkRepository linkRepository;

    @Override
    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> getLinksByUser(String user) {
        return List.of(new Link[0]);
    }

    @Override
    public Link getLinkById(Long id) {
        return null;
    }

    @Override
    public Link createLink(Link link) {return null;}


    @Override
    public Link updateLink(Link link) {
        return null;
    }

    @Override
    public void deleteLink(Long id) {

    }
}
