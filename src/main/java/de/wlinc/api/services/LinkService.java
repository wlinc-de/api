package de.wlinc.api.services;

import de.wlinc.api.entity.Link;

import java.util.List;

public interface LinkService {
    public List<Link> getAllLinks();
    public List<Link> getLinksByUser(String user);
    public Link getLinkById(Long id);
    public Link createLink(Link link);
    public Link updateLink(Link link);
    public void deleteLink(Long id);
}
