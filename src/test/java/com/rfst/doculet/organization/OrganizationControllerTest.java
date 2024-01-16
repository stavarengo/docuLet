package com.rfst.doculet.organization;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrganizationService organizationService;

    @Test
    @WithMockUser
    void whenOrgDoesNotExist_thenReturns404() throws Exception {
        mockMvc.perform(get("/organization/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
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
}