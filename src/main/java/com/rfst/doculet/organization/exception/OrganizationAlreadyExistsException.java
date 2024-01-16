package com.rfst.doculet.organization.exception;

import com.rfst.doculet.organization.Organization;

public class OrganizationAlreadyExistsException extends Exception {
    private OrganizationAlreadyExistsException(String message) {
        super(message);
    }

    public static OrganizationAlreadyExistsException create(Organization organization) {
        return new OrganizationAlreadyExistsException(String.format("There is already an organization with country '%s' and CRD '%s'.", organization.getCountry(), organization.getCrd()));
    }
}