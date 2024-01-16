package com.rfst.doculet.organization;

import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Organization findById(long id);
}
