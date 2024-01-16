package com.rfst.doculet.organization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
    public Optional<Organization> byId(Long id) {
        return organizationRepository.findById(id);
    }
    public Iterable<Organization> all() {
        return organizationRepository.findAll();
    }
}
