package bb.org.petclinic.controllers;

import bb.org.petclinic.model.Owner;
import bb.org.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
//@RequestMapping("/owner")
public class OwnerController {

    //private Set<Owner> owners;
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    //@RequestMapping({"","/", "/index", "/index.html"})
    @RequestMapping({"/owners","/owners/index", "/owners/index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }
}
