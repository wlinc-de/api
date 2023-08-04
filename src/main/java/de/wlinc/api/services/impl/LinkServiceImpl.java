package de.wlinc.api.services.impl;

import de.wlinc.api.entity.Link;
import de.wlinc.api.repositories.LinkRepository;
import de.wlinc.api.services.LinkService;
import de.wlinc.api.util.PermissionChecker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    final LinkRepository linkRepository;
    final PermissionChecker permissionChecker;

    @Value("${wlinc.link.token.length}")
    private int tokenLength = 7;

    @Value("${wlinc.link.token.containsNumbers}")
    private boolean tokenContainsNumbers = true;

    @Value("${wlinc.link.token.containsLetters}")
    private boolean tokenContainsLetters = true;

    @Override
    public List<Link> getAllLinks(Jwt jwt) {
        permissionChecker.hasUserOneOfRoles(jwt, new String[]{});

        return (List<Link>) linkRepository.findAll();
    }

    @Override
    public List<Link> getLinksByUser(Jwt jwt, String user) {
        permissionChecker.isUserSelfOrAdmin(jwt, user);
        return linkRepository.getLinksByUser(user);
    }

    @Override
    public Link getLinkByToken(Jwt jwt, String token) {
        var link = linkRepository.getLinkByToken(token);
        if (link.isPresent()) {
            return link.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Link not found");
        }
    }

    @Override
    public Link getLinkById(Jwt jwt, Long id) {
        var link = linkRepository.findById(id);
        if(link.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Link not found");
        }
        permissionChecker.isUserSelfOrAdmin(jwt, link.get().getUser());
        return link.get();
    }

    @Override
    public Link createLink(Jwt jwt, Link link) {
        permissionChecker.hasUserOneOfRoles(jwt, new String[]{"create_links"});
        var token = link.getToken();
        if(token == null || token.isEmpty()) {
            token = generateToken();
            System.out.println("Generated token: " + token);
            link.setToken(token);
        }
        if(linkRepository.getLinkByToken(token).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Token already exists");
        }
        link.setCreatedAt(LocalDateTime.now());

        link.setUser(jwt.getSubject());

        return linkRepository.save(link);
    }


    @Override
    public Link updateLink(Jwt jwt, Link link) {
        return null;
    }

    @Override
    public void deleteLink(Jwt jwt, Long id) {

    }

    private String generateToken() {
        int counter = 0;
        int maxTries = tokenLength^(tokenContainsLetters ? 26 : 0)^(tokenContainsNumbers ? 10 : 0);
        while (true){
            counter++;
            if(counter > maxTries){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not generate token, max tries exceeded");
            }
            var token = RandomStringUtils.random(tokenLength, tokenContainsLetters, tokenContainsNumbers);
            if(linkRepository.getLinkByToken(token).isEmpty()){
                return token;
            }
        }
    }
}
