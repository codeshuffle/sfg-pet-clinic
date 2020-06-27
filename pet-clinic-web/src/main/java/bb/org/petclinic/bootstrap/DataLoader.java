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

    public DataLoader() {
        ownerService = new OwnerServiceMap() ;
        vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner o1=new Owner();
        o1.setId(1L);
        o1.setFirstName("John");
        o1.setLastName("FirstOwner");

        Owner o2=new Owner();
        o2.setId(2L);
        o2.setFirstName("Steve");
        o2.setLastName("SecondtOwner");

        ownerService.save(o1);
        ownerService.save(o2);
        System.out.println("Loaded owners");

        Vet v1= new Vet();
        v1.setId(1L);
        v1.setFirstName("Boris");
        v1.setLastName("FirstVet");

        Vet v2= new Vet();
        v2.setId(2L);
        v2.setFirstName("Mark");
        v2.setLastName("SecondVet");

        vetService.save(v1);
        vetService.save(v2);
        System.out.println("Loaded vets");

    }
}
