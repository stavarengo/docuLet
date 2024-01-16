package com.rfst.doculet.organization;

import com.rfst.doculet.organization.exception.OrganizationAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrganizationServiceTest {
    @MockBean
    OrganizationRepository organizationRepository;

    @Autowired
    OrganizationService organizationService;

    @Test
    void whenOrgAlreadyExists_thenThrowsException() {
        var org = new Organization();
        org.setCountry("NL");
        org.setCrd("123456789");

        Mockito.when(organizationRepository.existsByCountryAndCrd(org.getCountry(), org.getCrd()))
                .thenReturn(true);
        assertThrows(OrganizationAlreadyExistsException.class, () -> organizationService.create(org));
    }
}