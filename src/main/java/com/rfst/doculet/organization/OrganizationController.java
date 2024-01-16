package com.rfst.doculet.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/{id}")
    public Organization getOrganization(@PathVariable Long id) {
        return organizationService.byId(id).orElseThrow(() -> new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                String.format("Organization with id %d not found", id)
        ));
    }
    @GetMapping
    public Iterable<Organization> listAllOrganizations() {
        return organizationService.all();
    }
}
