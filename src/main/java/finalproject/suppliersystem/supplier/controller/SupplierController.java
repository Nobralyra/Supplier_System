package finalproject.suppliersystem.supplier.controller;

import finalproject.suppliersystem.supplier.domain.Supplier;
import finalproject.suppliersystem.supplier.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/supplier/register_supplier")
    public String showRegisterSupplier(Supplier supplier, Model model){
        model.addAttribute("supplier", supplier);
        return "/supplier/register_supplier";
    }

    /*
        @PostMapping("/bruger/opret_bruger")
    public String createUser(@RequestParam String password2, @Valid User user, BindingResult bindingResult, Model model){

        List<User> alUsers = userService.findAll();

        boolean createdAlready = false;
        String brugernavn = "";
        for(User u : alUsers){
            if(u.getFirstName().equals(user.getFirstName())
                    && u.getLastName().equals(user.getLastName())
                    && u.getEmailAddress().equals(user.getEmailAddress())){
                createdAlready = true;
                brugernavn = u.getUsername();
                break;
            }
        }
        if(createdAlready){
            String created = "Brugeren er allerede oprettet med brugernavnet: " + brugernavn;
            model.addAttribute("created", created);
            return "/bruger/opret_bruger";
        }

        userService.save(user);
        Long id = user.getId();
        return "redirect:/bruger/bruger_side/" + id;
    }
     */

    @PostMapping("/supplier/register_supplier")
    public String registerSupplier(@Valid Supplier supplier, BindingResult bindingResult, Model model){

        if(supplierService.hasErrors(bindingResult, model)) return "/supplier/register_supplier";
        List<Supplier> alSuppliers = supplierService.findAll();

        return "tester";
    }




}


