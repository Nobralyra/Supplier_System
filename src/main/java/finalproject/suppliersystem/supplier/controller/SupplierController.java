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
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
     * validate with Valid/BindingResult and ask CountryService if a
     * Though, ProductCategories are not created in this connection, but the user
     * only chooses one og several of them. So they are not validated here.
     *
     * @param supplier
     * @param contactInformation
     * @param address
     * @param contactPerson
     * @param bindingResultSupplier
     * @param bindingResultContactInformation
     * @param bindingResultAddress
     * @param bindingResultContactPerson
     * @param model
     * @return
     */

    @PostMapping("/registration/supplier")
    public String registerSupplier(@Valid Supplier supplier,
                                   BindingResult bindingResultSupplier,
                                   @Valid ContactInformation contactInformation,
                                   BindingResult bindingResultContactInformation,
                                   @Valid Address address,
                                   BindingResult bindingResultAddress,
                                   @Valid ContactPerson contactPerson,
                                   BindingResult bindingResultContactPerson,
                                   @Valid Country country,
                                   BindingResult bindingResultCountry,
                                   Model model)
    {

        if(supplierService.hasErrors(bindingResultSupplier,
                bindingResultContactInformation,
                bindingResultAddress,
                bindingResultContactPerson,
                bindingResultCountry, model)){
            model.addAttribute("productCategory", productCategoryService.findAll());
            return "/registration/supplier";
        }

        if (supplierService.existAlready(supplier, address, country))
        {
            String alreadyCreated = "Supplier is already registered";
            model.addAttribute("productCategory", productCategoryService.findAll());
            model.addAttribute("alreadyCreated", alreadyCreated);
            return "/registration/supplier";
        }

        contactInformation.setSupplier(supplier);
        address.setContactInformation(contactInformation);

        address.setCountry(countryService.checkUniqueCountryName(country));
        contactPerson.setContactInformation(contactInformation);

        countryService.save(country);
        addressService.save(address);
        contactPersonService.save(contactPerson);
        contactInformationService.save(contactInformation);

        supplierService.save(supplier);

        Long supplierId = supplier.getSupplierId();
        return "redirect:/registration/supplier_confirmation/" + supplierId;
    }

    @GetMapping("/registration/supplier_confirmation/{supplierId}")
    public String confirmRegistration(@PathVariable("supplierId") Long supplierId, Model model)
    {

        Long supplierNumber = supplierService.findById(supplierId).getSupplierId();
        model.addAttribute("confirmation", "SupplierNumber " + supplierNumber + " is registred.");
        return "/registration/supplier_confirmation";
    }


    /**
     * This is used, when the user wants to register two contact persons
     * It stores the supplier and leads to the other html-page, where
     * the user can register an extra contact person
     *
     * @param supplier
     * @param bindingResultSupplier
     * @param contactInformation
     * @param bindingResultContactInformation
     * @param address
     * @param bindingResultAddress
     * @param contactPerson
     * @param bindingResultContactPerson
     * @param country
     * @param bindingResultCountry
     * @param model
     * @return
     */

    @PostMapping("/registration/supplier_with_extra_contact_person")
    public String registerSupplierWithExtraContactperson(@Valid Supplier supplier,
                                                         BindingResult bindingResultSupplier,
                                                         @Valid ContactInformation contactInformation,
                                                         BindingResult bindingResultContactInformation,
                                                         @Valid Address address,
                                                         BindingResult bindingResultAddress,
                                                         @Valid ContactPerson contactPerson,
                                                         BindingResult bindingResultContactPerson,
                                                         @Valid Country country,
                                                         BindingResult bindingResultCountry,
                                                         Model model)
    {

        if(supplierService.hasErrors(bindingResultSupplier,
                bindingResultContactInformation,
                bindingResultAddress,
                bindingResultContactPerson,
                bindingResultCountry, model)){
            model.addAttribute("productCategory", productCategoryService.findAll());
            return "/registration/supplier";
        }

        if (supplierService.existAlready(supplier, address, country))
        {
            String alreadyCreated = "Supplier is already registered";
            model.addAttribute("productCategory", productCategoryService.findAll());
            model.addAttribute("alreadyCreated", alreadyCreated);
            return "/registration/supplier";
        }

        contactInformation.setSupplier(supplier);
        address.setContactInformation(contactInformation);

        address.setCountry(countryService.checkUniqueCountryName(country));
        contactPerson.setContactInformation(contactInformation);

        countryService.save(country);
        addressService.save(address);
        contactPersonService.save(contactPerson);
        contactInformationService.save(contactInformation);

        supplierService.save(supplier);

        Long supplierId = supplier.getSupplierId();
        return "redirect:/registration/supplier_with_extra_contact_person/" + supplierId;
    }


    /**
     * This mapping sends the user to the page, where the user can register
     * an extra contact person
     * @param supplierId
     * @param contactPerson
     * @param model
     * @return
     */
    @GetMapping("/registration/supplier_with_extra_contact_person/{supplierId}")
    public String showNewRegistrationPage(@PathVariable("supplierId") Long supplierId,
                                          ContactPerson contactPerson, Model model){

        Supplier supplier = supplierService.findById(supplierId);
        ContactInformation contactInformation= contactInformationService.findById(supplierId);
        Address address = addressService.findById(supplierId);
        ContactPerson contactPersonAlready = contactPersonService.findBySupplierId(supplierId);
        Long countryId = address.getCountry().getCountryId();
        Country country = countryService.findById(countryId);

        model.addAttribute("supplier", supplier);
        model.addAttribute("contactInformation", contactInformation);
        model.addAttribute("address", address);
        model.addAttribute("country", country);
        model.addAttribute("contactPersonAlready", contactPersonAlready);
        model.addAttribute("contactPerson", contactPerson);

        return "/registration/supplier_with_extra_contact_person";
    }

    /**
     * This mapping registres the extra contact person
     * @param supplierId
     * @param contactPerson
     * @param bindingResultContactPerson
     * @param model
     * @return
     */
    @PostMapping("/registration/register_extra_person/{supplierId}")
    public String showConfirmation(@PathVariable("supplierId") Long supplierId,
                                   @Valid ContactPerson contactPerson,
                                   BindingResult bindingResultContactPerson,
                                   Model model){

        if(bindingResultContactPerson.hasErrors()){
            return "/registration/supplier_with_extra_contact_person";
        }

        ContactInformation contactInformation = contactInformationService.findById(supplierId);
        contactPerson.setContactInformation(contactInformation);
        contactPersonService.save(contactPerson);

        model.addAttribute("confirmation", "SupplierNumber " + supplierId + " is registred.");
        return "/registration/supplier_confirmation";
    }
}


