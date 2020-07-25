package bb.org.petclinic.bootstrap;

import bb.org.petclinic.model.Owner;
import bb.org.petclinic.model.Vet;
import bb.org.petclinic.service.OwnerService;
import bb.org.petclinic.service.VetService;
import bb.org.petclinic.service.map.OwnerServiceMap;
import bb.org.petclinic.service.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner o1=new Owner();

        o1.setFirstName("John");
        o1.setLastName("FirstOwner");

        Owner o2=new Owner();

        o2.setFirstName("Steve");
        o2.setLastName("SecondtOwner");

        ownerService.save(o1);
        ownerService.save(o2);
        System.out.println("Loaded owners");

        Vet v1= new Vet();

        v1.setFirstName("Boris");
        v1.setLastName("FirstVet");

        Vet v2= new Vet();

        v2.setFirstName("Mark");
        v2.setLastName("SecondVet");

        vetService.save(v1);
        System.out.println(vetService.save(v2).toString());
        System.out.println("Loaded vets");

    }
}
