package bb.org.petclinic.controllers;

import bb.org.petclinic.model.Owner;
import bb.org.petclinic.service.OwnerService;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService service;

    Model model;

    @InjectMocks
    OwnerController controller;

    MockMvc mockMvc;

    Set<Owner> owners ;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        Owner returnOwner = new Owner();
        returnOwner.setId(1L);
        returnOwner.setLastName("Smith");

        Owner secondOwner = new Owner();
        secondOwner.setId(2L);
        secondOwner.setLastName("John");

        owners.add(returnOwner);
        owners.add(secondOwner);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();


    }

    @Test
    void listOwners() throws Exception {
        when(service.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners")).andExpect(status().is2xxSuccessful()).
                andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void findOwners() throws Exception {

        mockMvc.perform(get("/owners/find")).andExpect(status().is2xxSuccessful()).
                andExpect(view().name("notimplementedyet"));

        verifyZeroInteractions(service);
    }
}