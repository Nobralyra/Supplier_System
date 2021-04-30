package finalproject.suppliersystem.supplier.controller;

import finalproject.suppliersystem.supplier.domain.Address;
import finalproject.suppliersystem.supplier.domain.ContactInformation;
import finalproject.suppliersystem.supplier.domain.ContactPerson;
import finalproject.suppliersystem.supplier.domain.Supplier;
import finalproject.suppliersystem.supplier.service.ProductCategoryService;
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
    private final ProductCategoryService productCategoryService;

    public SupplierController(SupplierService supplierService, ProductCategoryService productCategoryService) {
        this.supplierService = supplierService;
        this.productCategoryService = productCategoryService;
    }

    /**
     * Ville det være en ide at ændre til /registration/supplier i stedet for?
     * Det samme med den mappe HTML'en ligger i?
     * @param supplier
     * @param model
     * @return
     */
    @GetMapping("/registration/supplier")
    public String showRegisterSupplier(Supplier supplier, ContactInformation contactInformation, Address address, ContactPerson contactPerson, Model model){
        model.addAttribute("supplier", supplier);
        model.addAttribute("contactInformation", contactInformation);
        model.addAttribute("address", address);
        model.addAttribute("contactPerson", contactPerson);

        // her kunne en product Category findAll() godt give mening, men skal den have sin egen service og repository?
        model.addAttribute("productCategory", productCategoryService.findAll());
        return "/registration/supplier";
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

    @PostMapping("/registration/supplier")
    public String registerSupplier(@Valid Supplier supplier, BindingResult bindingResult, Model model){

        if(supplierService.hasErrors(bindingResult, model)) return "/registration/supplier";
        List<Supplier> alSuppliers = supplierService.findAll();

        return "tester";
    }




}


