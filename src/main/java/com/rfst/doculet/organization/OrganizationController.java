package com.rfst.doculet.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        Organization createdOrg = organizationService.create(organization);

        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdOrg.getId()).toUri();
        return ResponseEntity.created(location).body(createdOrg);
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
