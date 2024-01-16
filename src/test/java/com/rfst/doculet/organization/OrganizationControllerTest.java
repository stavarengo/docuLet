package com.rfst.doculet.organization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rfst.doculet.organization.exception.OrganizationAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrganizationService organizationService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenOrgDoesNotExist_thenReturns404() throws Exception {
        mockMvc.perform(get("/organization/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenOrgExists_thenReturnsOrg() throws Exception {
        var org = new Organization();
        org.setId(1L);
        org.setCountry("NL");
        org.setCrd("123456789");
        Mockito.when(organizationService.byId(org.getId()))
                .thenReturn(Optional.of(org));
        String urlTemplate = "/organization/" + org.getId() ;
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("NL"))
                .andExpect(jsonPath("$.crd").value("123456789"))
                .andExpect(jsonPath("$.id").value(1L));

    }

    @Test
    void whenDbIsEmpty_ThenListAllOrgsReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/organization"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void whenDbHasOrgs_ThenListAllOrgsReturnsAllOrgs() throws Exception {
        var org1 = new Organization();
        org1.setId(1L);
        org1.setCountry("NL");
        org1.setCrd("123456789");
        var org2 = new Organization();
        org2.setId(2L);
        org2.setCountry("BE");
        org2.setCrd("987654321");
        Mockito.when(organizationService.all())
                .thenReturn(List.of(org1, org2));
        mockMvc.perform(get("/organization"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value(org1.getCountry()))
                .andExpect(jsonPath("$[0].crd").value(org1.getCrd()))
                .andExpect(jsonPath("$[0].id").value(org1.getId()))
                .andExpect(jsonPath("$[1].country").value(org2.getCountry()))
                .andExpect(jsonPath("$[1].crd").value(org2.getCrd()))
                .andExpect(jsonPath("$[1].id").value(org2.getId()));

    }

    @Test
    void createOrganization() throws Exception {
        var orgForRequest = new Organization();
        orgForRequest.setCountry("NL");
        orgForRequest.setCrd("123456789");

        var createdOrg = new Organization();
        createdOrg.setId(RandomGenerator.getDefault().nextLong());
        createdOrg.setCountry(orgForRequest.getCountry());
        createdOrg.setCrd(orgForRequest.getCrd());

        Mockito.when(organizationService.create(any(Organization.class)))
                .thenReturn(createdOrg);

        mockMvc.perform(post("/organization")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orgForRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country").value(createdOrg.getCountry()))
                .andExpect(jsonPath("$.crd").value(createdOrg.getCrd()))
                .andExpect(jsonPath("$.id").value(createdOrg.getId()));
    }

    @Test
    void whenThereIsAlreadyAnOrgWithSameCrdAndCountry_thenReturns409() throws Exception {
        var org = new Organization();
        org.setCountry("NL");
        org.setCrd("123456789");

        Mockito.when(organizationService.create(any(Organization.class)))
                .thenThrow(OrganizationAlreadyExistsException.create(org));

        mockMvc.perform(post("/organization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(org)))
                .andExpect(status().isConflict());
    }
}