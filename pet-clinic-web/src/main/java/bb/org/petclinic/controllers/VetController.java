package bb.org.petclinic.controllers;

import bb.org.petclinic.model.Owner;
import bb.org.petclinic.model.Vet;
import bb.org.petclinic.service.VetService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
//@RequestMapping("/vets")
public class VetController {

    //private Set<Vet> vets;
    private final VetService vetService;

    //public VetController(@Qualifier("mapVetService")  VetService vetService) {
     //   this.vetService = vetService;
    //}


    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets/index","/vets.html"})
    public String listVets(Model model)
    {
        model.addAttribute("vets", vetService.findAll());
        return  "vets/index";
    }

    @RequestMapping("/api/vets")
    public @ResponseBody  Set<Vet>  getVetsJson() {

        return vetService.findAll();
    }
}
