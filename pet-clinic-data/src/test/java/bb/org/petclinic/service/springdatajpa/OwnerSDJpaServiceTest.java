package bb.org.petclinic.service.springdatajpa;

import bb.org.petclinic.model.Owner;
import bb.org.petclinic.repositories.OwnerRepository;
import bb.org.petclinic.repositories.PetRepository;
import bb.org.petclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returnOwner, secondOwner;
    Set<Owner> ownerSet = new HashSet<>();

    @BeforeEach
    void setUp() {


        returnOwner = new Owner();
        returnOwner.setId(1L);
        returnOwner.setLastName("Smith");

        secondOwner = new Owner();
        secondOwner.setId(2L);
        secondOwner.setLastName("John");

        ownerSet.add(returnOwner);
        ownerSet.add(secondOwner);
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner smith = service.findByLastName("Smith");
        assertEquals("Smith", smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        when(ownerRepository.findAll()).thenReturn(ownerSet);
        Set<Owner> owners = service.findAll();

        assertNotNull(owners);
        assertEquals(2L, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(returnOwner));
        Owner owner = service.findById(1L);
        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());
        Owner owner = service.findById(1L);
        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = new Owner();
        ownerToSave.setId(3L);
        ownerToSave.setLastName("Newly saved");
        when(ownerRepository.save(any())).thenReturn(ownerToSave);
        Owner savedOwner = service.save(ownerToSave);

        assertNotNull(savedOwner);
        assertEquals(ownerToSave.getId(), savedOwner.getId());

        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        service.delete(returnOwner);
        //times equals 1 by default actually
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }
}