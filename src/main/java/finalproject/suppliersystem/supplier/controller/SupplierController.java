package finalproject.suppliersystem.supplier.controller;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.*;
import finalproject.suppliersystem.supplier.errorcheck.IExistsAlready;
import finalproject.suppliersystem.supplier.errorcheck.IHasError;
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

    private final IService<Supplier> iSupplierService;
    private final IService<Criticality> iCriticalityService;
    private final IService<Address> iAddressService;
    private final IService<ContactInformation> iContactInformationService;
    private final IContactPersonService iContactPersonService;
    private final IService<Country> iCountryService;
    private final IService<ProductCategory> iProductCategoryService;
    private final IExistsAlready iExistsAlready;
    private final IHasError iHasError;

    @Autowired
    public SupplierController(IService<Supplier> iSupplierService,
                              IService<Criticality> iCriticalityService,
                              IService<Address> iAddressService,
                              IService<ContactInformation> iContactInformationService,
                              IContactPersonService iContactPersonService,
                              IService<Country> iCountryService,
                              IService<ProductCategory> iProductCategoryService,
                              IExistsAlready iExistsAlready,
                              IHasError iHasError)
    {
        this.iSupplierService = iSupplierService;
        this.iCriticalityService = iCriticalityService;
        this.iAddressService = iAddressService;
        this.iContactInformationService = iContactInformationService;
        this.iContactPersonService = iContactPersonService;
        this.iCountryService = iCountryService;
        this.iProductCategoryService = iProductCategoryService;
        this.iExistsAlready = iExistsAlready;
        this.iHasError = iHasError;
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
        model.addAttribute("productCategory", iProductCategoryService.findAll());
        model.addAttribute("countries", iCountryService.findAll());
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
                                   Model model)
    {

        if(iHasError.hasErrors(bindingResultSupplier,
                bindingResultCriticality,
                bindingResultContactInformation,
                bindingResultAddress,
                bindingResultContactPerson,
                model)){
            model.addAttribute("productCategory", iProductCategoryService.findAll());
            model.addAttribute("countries", iCountryService.findAll());
            return "/registration/supplier";
        }

        if (iExistsAlready.existAlready(supplier, address))
        {
            String alreadyCreated = "Supplier is already registered";
            model.addAttribute("productCategory", iProductCategoryService.findAll());
            model.addAttribute("countries", iCountryService.findAll());
            model.addAttribute("alreadyCreated", alreadyCreated);
            return "/registration/supplier";
        }

        contactInformation.setSupplier(supplier);
        address.setContactInformation(contactInformation);

        contactPerson.setContactInformation(contactInformation);

        criticality.setSupplier(supplier);

        iAddressService.save(address);
        iContactPersonService.save(contactPerson);
        iContactInformationService.save(contactInformation);

        iCriticalityService.save(criticality);

        iSupplierService.save(supplier);

        Long supplierId = supplier.getSupplierId();

        //https://stackoverflow.com/questions/52243656/spring-requestmapping-multiple-paths-identify-which-one-is-being-called/52243753

        String path = "";
        try {
            URL url = new URL(request.getRequestURL().toString());
            path = url.getPath();
        }catch (MalformedURLException malformedURLException){
            System.err.println(malformedURLException);
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
        Long supplierNumber = iSupplierService.findById(supplierId).getSupplierId();
        model.addAttribute("confirmation", "SupplierNumber " + supplierNumber + " is registered.");
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

        Supplier supplier = iSupplierService.findById(supplierId);
        ContactPerson contactPersonAlready = iContactPersonService.findBySupplierId(supplierId);
        model.addAttribute("supplier", supplier);
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
            model.addAttribute("contactPerson", contactPerson);
            Supplier supplier = iSupplierService.findById(supplierId);
            model.addAttribute("supplier", supplier);
            return "/registration/supplier_with_extra_contact_person";
        }

        ContactInformation contactInformation = iContactInformationService.findById(supplierId);
        contactPerson.setContactInformation(contactInformation);
        iContactPersonService.save(contactPerson);

        model.addAttribute("confirmation", "SupplierNumber " + supplierId + " is registered.");
        return "/registration/supplier_confirmation";
    }
}


