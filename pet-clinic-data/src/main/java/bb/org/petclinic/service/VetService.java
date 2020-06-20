package bb.org.petclinic.service;

import bb.org.petclinic.model.Pet;
import bb.org.petclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet findById(Long id);
    Vet save(Vet vet);
    Set<Vet> findAll();
}
