package bb.org.petclinic.controllers;

import bb.org.petclinic.model.Owner;
import bb.org.petclinic.service.OwnerService;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService service;

    Model model;

    @InjectMocks
    OwnerController controller;

    MockMvc mockMvc;

    List<Owner> owners ;

    @BeforeEach
    void setUp() {
        owners = new ArrayList<>();
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
        when(service.findAllByLastNameLike(anyString())).thenReturn((List<Owner>) owners);
        mockMvc.perform(get("/owners")).andExpect(status().is2xxSuccessful()).
                andExpect(view().name("owners/ownersList"));

    }



    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(service);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        Owner o1 = new Owner();
        when(service.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1l).build(),
                        Owner.builder().id(2l).build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        Owner o3 = new Owner();
        o3.setId(3l);
        List<Owner> o = new ArrayList<>();
        o.add(o3);
        when(service.findAllByLastNameLike(anyString())).thenReturn(o);
        //when(service.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList( Owner.builder().id(3l).build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/3"));
    }

    @Test
    void processFindFormEmptyReturnMany() throws Exception {
        when(service.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1l).build(),
                        Owner.builder().id(2l).build()));

        mockMvc.perform(get("/owners")
                .param("lastName",""))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));;
    }

    @Test
    void displayOwner() throws Exception {

        Owner owner = new Owner();
        owner.setId(3L);
        owner.setLastName("Borg");

        when(service.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/3")).andExpect(status().is2xxSuccessful()).
                andExpect(view().name("owners/ownerDetails"))
                .andExpect(model(). attribute("owner", hasProperty("id", is(3L))));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(service);
    }

    @Test
    void processCreationForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setLastName("Borg");

        //when(service.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1l).build());
        when(service.save(ArgumentMatchers.any())).thenReturn(owner);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(service).save(ArgumentMatchers.any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        when(service.findById(anyLong())).thenReturn(Owner.builder().id(1l).build());

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(service);
    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setLastName("Borg");

        //when(service.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1l).build());
        when(service.save(ArgumentMatchers.any())).thenReturn(owner);

        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(service).save(ArgumentMatchers.any());
    }
}