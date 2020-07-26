package bb.org.petclinic.bootstrap;

import bb.org.petclinic.model.*;
import bb.org.petclinic.service.OwnerService;
import bb.org.petclinic.service.PetTypeService;
import bb.org.petclinic.service.SpecialityService;
import bb.org.petclinic.service.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }

    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology = specialityService.save(radiology);
        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);
        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");
        //Speciality savedSurgery = specialityService.save(surgery);

        System.out.println("Loaded pet types");

        Owner o1 = new Owner();

        o1.setFirstName("John");
        o1.setLastName("FirstOwner");
        o1.setAddress("123 SOme Street");
        o1.setCity("Atlanta");
        o1.setTelephone("546321112");

        Pet johnsPet = new Pet();
        johnsPet.setPetType(savedDogType);
        johnsPet.setOwner(o1);
        johnsPet.setBirthDate(LocalDate.now());
        johnsPet.setName("Canis");
        o1.getPets().add(johnsPet);

        Owner o2 = new Owner();

        o2.setFirstName("Steve");
        o2.setLastName("SecondtOwner");
        o2.setAddress("456 Another Street");
        o2.setCity("San Francisco");
        o2.setTelephone("87955112");

        Pet stevesPet = new Pet();
        stevesPet.setPetType(savedCatType);
        stevesPet.setOwner(o2);
        stevesPet.setBirthDate(LocalDate.now());
        stevesPet.setName("Meaow");
        o2.getPets().add(stevesPet);

        ownerService.save(o1);
        ownerService.save(o2);
        System.out.println("Loaded owners");

        Vet v1 = new Vet();

        v1.setFirstName("Boris");
        v1.setLastName("FirstVet");
        v1.getSpecialities().add(savedRadiology);

        Vet v2 = new Vet();

        v2.setFirstName("Mark");
        v2.setLastName("SecondVet");
        v1.getSpecialities().add(savedDentistry);

        vetService.save(v1);
        System.out.println(vetService.save(v2).toString());
        System.out.println("Loaded vets");
    }
}
