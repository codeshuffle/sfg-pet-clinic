package bb.org.petclinic.service.map;

import bb.org.petclinic.model.Owner;
import bb.org.petclinic.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class OwnerMapServiceTest {

    OwnerMapService ownerMapService;

    final Long ownerId =1L;
    final String lastName = "Smith";

//    @Mock
//    PetTypeMapService petTypeMapService;
//    @Mock
//    PetMapService petMapService;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.initMocks(this);
//        ownerMapService= new OwnerMapService<>(petTypeMapService, petMapService);
        ownerMapService= new OwnerMapService(new PetTypeMapService(), new PetMapService());

        Owner someOwner = new Owner();
        someOwner.setId(1L);
        someOwner.setLastName("Smith");
        ownerMapService.save(someOwner);
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet= ownerMapService.findAll();
        assertEquals(1, ownerSet.size());
        //ownerSet.forEach(ownerSet::add);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(1, owner.getId());
    }

    @Test
    void save() {
        Owner owner2 = Owner.builder().city("Oradea").build();
        Owner savedOwner =ownerMapService.save(owner2);
        assertEquals(owner2, savedOwner);
        assertEquals(2L, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner owner3 = Owner.builder().build();
        Owner savedOwner =ownerMapService.save(owner3);
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName(lastName);
        assertNotNull(smith);
        assertEquals(ownerId, smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = ownerMapService.findByLastName("foo");
        assertNull(smith);
            }
}