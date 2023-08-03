package de.wlinc.api.controller;

import de.wlinc.api.entity.Link;
import de.wlinc.api.services.impl.LinkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/link")
public class LinkController {

    LinkServiceImpl linkService;

    @GetMapping("/all")
    public List<Link> getLinks() {
        return linkService.getAllLinks();
    }

    @PostMapping
    public Link createLink(Link link) {
        return linkService.createLink(link);
    }
}
