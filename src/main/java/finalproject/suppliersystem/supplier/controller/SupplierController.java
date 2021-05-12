package finalproject.suppliersystem.supplier.controller;

import finalproject.suppliersystem.core.enums.CorporateSocialResponsibility;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
@Transactional
public class SupplierController
{

    private final SupplierService supplierService;
    private final CriticalityService criticalityService;
    private final AddressService addressService;
    private final ContactInformationService contactInformationService;
    private final ContactPersonService contactPersonService;
    private final CountryService countryService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public SupplierController(SupplierService supplierService,
                              CriticalityService criticalityService, AddressService addressService,
                              ContactInformationService contactInformationService,
                              ContactPersonService contactPersonService,
                              CountryService countryService, ProductCategoryService productCategoryService)
    {
        this.supplierService = supplierService;
        this.criticalityService = criticalityService;
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
     * @param criticality
     * @param contactInformation
     * @param address
     * @param contactPerson
     * @param model
     * @return registration/supplier-HTML
     */
    @GetMapping("/registration/supplier")
    public String showRegisterSupplier(Supplier supplier,
                                       Criticality criticality,
                                       ContactInformation contactInformation,
                                       Address address,
                                       ContactPerson contactPerson,
                                       Country country,
                                       Model model)
    {


        model.addAttribute("supplier", supplier);
        model.addAttribute("criticality", criticality);
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
     * This PostMapping handles two url with HttpServletRequest, so it returns different
     * HTML-pages depending the path.
     *
     * @param request
     * @param supplier
     * @param criticality
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

    @PostMapping(value={"/registration/supplier", "/registration/supplier_with_extra_contact_person"})
    public String registerSupplier(HttpServletRequest request, @Valid Supplier supplier,
                                   BindingResult bindingResultSupplier,
                                   @Valid Criticality criticality,
                                   BindingResult bindingResultCriticality,
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
                bindingResultCriticality,
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

        criticality.setSupplier(supplier);

        countryService.save(country);
        addressService.save(address);
        contactPersonService.save(contactPerson);
        contactInformationService.save(contactInformation);

        criticalityService.save(criticality);

        supplierService.save(supplier);

        Long supplierId = supplier.getSupplierId();

        //https://stackoverflow.com/questions/52243656/spring-requestmapping-multiple-paths-identify-which-one-is-being-called/52243753

        String path = "";
        try {
            URL url = new URL(request.getRequestURL().toString());
            path = url.getPath();
        }catch (MalformedURLException ma){
            System.out.println(ma);
        }

        if (path.equals("/registration/supplier")) {
            return "redirect:/registration/supplier_confirmation/" + supplierId;

        } else if (path.equals("/registration/supplier_with_extra_contact_person")) {
            return "redirect:/registration/supplier_with_extra_contact_person/" + supplierId;
        }
        else return "login";

    }

    @GetMapping("/registration/supplier_confirmation/{supplierId}")
    public String confirmRegistration(@PathVariable("supplierId") Long supplierId, Model model)
    {

        Long supplierNumber = supplierService.findById(supplierId).getSupplierId();
        model.addAttribute("confirmation", "SupplierNumber " + supplierNumber + " is registred.");
        return "/registration/supplier_confirmation";
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
        Criticality criticality = criticalityService.findById(supplierId);
        ContactInformation contactInformation= contactInformationService.findById(supplierId);
        Address address = addressService.findById(supplierId);
        ContactPerson contactPersonAlready = contactPersonService.findBySupplierId(supplierId);
        Long countryId = address.getCountry().getCountryId();
        Country country = countryService.findById(countryId);

        model.addAttribute("supplier", supplier);
        model.addAttribute("criticality", criticality);
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


