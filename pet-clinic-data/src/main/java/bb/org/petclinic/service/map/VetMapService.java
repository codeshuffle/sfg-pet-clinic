package bb.org.petclinic.service.map;

import bb.org.petclinic.model.Speciality;
import bb.org.petclinic.model.Vet;
import bb.org.petclinic.service.SpecialityService;
import bb.org.petclinic.service.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("mapVetService")
@Profile({"default","map"})
public class VetMapService<T, ID> extends AbstractMapService<Vet, Long> implements VetService {
    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet object) {
        if (object.getSpecialities().size() > 0) {
            object.getSpecialities().forEach(speciality -> {
                if (speciality.getId() == null) {
                    Speciality savedSpeciality = specialityService.save(speciality);
                    speciality.setId(savedSpeciality.getId());
                }
            });
        }
            return super.save(object);
        }

        @Override
        public void delete (Vet object){
            super.delete(object);

        }

        @Override
        public void deleteById (Long id){
            super.deleteById(id);
        }

    }
