package finalproject.suppliersystem.supplier.controller;

import finalproject.suppliersystem.supplier.domain.*;
import finalproject.suppliersystem.supplier.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@Transactional
public class SupplierController
{

    private final SupplierService supplierService;
    private final AddressService addressService;
    private final ContactInformationService contactInformationService;
    private final ContactPersonService contactPersonService;
    private final CountryService countryService;
    private final ProductCategoryService productCategoryService;
    //@PersistenceContext
    //private EntityManager entityManager;

    @Autowired
    public SupplierController(SupplierService supplierService,
                              AddressService addressService,
                              ContactInformationService contactInformationService,
                              ContactPersonService contactPersonService,
                              CountryService countryService, ProductCategoryService productCategoryService)
    {
        this.supplierService = supplierService;
        this.addressService = addressService;
        this.contactInformationService = contactInformationService;
        this.contactPersonService = contactPersonService;
        this.countryService = countryService;
        this.productCategoryService = productCategoryService;
    }

    /**
     * Al entities containing information about the supplier is sent to
     * registration/supplier-site within the model
     *
     * @param supplier
     * @param contactInformation
     * @param address
     * @param contactPerson
     * @param model
     * @return registration/supplier-HTML
     */
    @GetMapping("/registration/supplier")
    public String showRegisterSupplier(Supplier supplier,
                                       ContactInformation contactInformation,
                                       Address address,
                                       ContactPerson contactPerson,
                                       Country country,
                                       Model model)
    {
        model.addAttribute("supplier", supplier);
        model.addAttribute("contactInformation", contactInformation);
        model.addAttribute("address", address);
        model.addAttribute("contactPerson", contactPerson);
        model.addAttribute("country", country);
        //the user chooses one or several ProductCategories
        model.addAttribute("productCategory", productCategoryService.findAll());
        return "/registration/supplier";
    }

    /**
     * PostMapping receives data from HTML and Controller asks SupplierService to
     * validate with Valid/BindingResult.
     * Though, ProductCategories are not created in this connection, but the user
     * only chooses one og several of them. So they are not validated either.
     *
     * @param supplier
     * @param contactInformation
     * @param address
     * @param contactPerson
     * @param productCategorySet
     * @param bindingResultSupplier
     * @param bindingResultContactInformation
     * @param bindingResultAddress
     * @param bindingResultContactPerson
     * @param model
     * @return
     */

    @PostMapping("/registration/supplier")
    public String registerSupplier(@Valid Supplier supplier,
                                   @Valid ContactInformation contactInformation,
                                   @Valid Address address,
                                   @Valid ContactPerson contactPerson,
                                   @Valid Country country,
                                   ProductCategory productCategorySet,
                                   BindingResult bindingResultCountryCallingCode,
                                   BindingResult bindingResultSupplier,
                                   BindingResult bindingResultContactInformation,
                                   BindingResult bindingResultAddress,
                                   BindingResult bindingResultContactPerson,
                                   BindingResult bindingResultCountry,
                                   Model model)
    {

        /*
          Fordi vi henter productKategorier fra databasen,
          bliver navnene på kategorierne ikke valideret her.
          Tjekker heller ikke callingCode eller Country her. Eller?

         */
        if (supplierService.hasErrors(bindingResultSupplier,
                bindingResultContactInformation,
                bindingResultAddress,
                bindingResultContactPerson))
        {
            return "/registration/supplier";
        }

        //created skal sættes ind på HTML-siden
        if (supplierService.existAlready(supplier, address))
        {
            String created = "Supplier is already registred with the supplier number: " + supplier.getSupplierNumber();
            model.addAttribute("created", created);
            return "/registration/supplier";
        }

        contactInformation.setSupplier(supplier);
        address.setContactInformation(contactInformation);
        address.setCountry(country);
        contactPerson.setContactInformation(contactInformation);

        countryService.save(country);
        addressService.save(address);
        contactPersonService.save(contactPerson);
        contactInformationService.save(contactInformation);
        supplierService.save(supplier);


        /*
          hvordan kommer de valgte kategorier fra HTML til PostMapping?
          Der bliver sendt et sæt med alle og brugeren vælger et eller flere af dem.
          Bliver det rigtigt nu? SortedSet er med i PostMapping og knyttes her til supplier:
        */
        //supplier.setProductCategorySet(productCategorySet);

        Long supplierId = supplier.getSupplierId();
        return "redirect:/registration/supplier_confirmation/" + supplierId;
    }

    //nu går dette til supplier confirmation-side,
    // men når vi har lavet forsiden, kan bekræftelsen sendes dertil
    @GetMapping("/registration/supplier_confirmation/{supplierId}")
    public String confirmRegistration(@PathVariable("supplierId") Long supplierId, Model model)
    {

        int supplierNumber = supplierService.findById(supplierId).getSupplierNumber();
        model.addAttribute("confirmation", "SupplierNumber " + supplierNumber + " is registred.");
        return "/registration/supplier_confirmation";
    }

}


