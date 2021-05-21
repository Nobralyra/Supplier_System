package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.Address;
import finalproject.suppliersystem.supplier.domain.Supplier;
import finalproject.suppliersystem.supplier.repository.IAddressRepository;
import finalproject.suppliersystem.supplier.repository.ISupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements IService<Supplier> {

    private final ISupplierRepository iSupplierRepository;
    private final IAddressRepository iAddressRepository;

    public SupplierService(ISupplierRepository iSupplierRepository, IAddressRepository iAddressRepository) {
        this.iSupplierRepository = iSupplierRepository;
        this.iAddressRepository = iAddressRepository;
    }

    /**
     * stores the given supplier in data base
     * @param supplier
     */
    @Override
    public void save(Supplier supplier) {
        iSupplierRepository.save(supplier);
    }

    /**
     * Returns true or false for errors in validation
     *
     * @param bindingResultSupplier
     * @param bindingResultContactInformation
     * @param bindingResultAddress
     * @param bindingResultContactPerson
     * @return boolean
     */
    public boolean hasErrors(BindingResult bindingResultSupplier,
                             BindingResult bindingResultCriticality,
                             BindingResult bindingResultContactInformation,
                             BindingResult bindingResultAddress,
                             BindingResult bindingResultContactPerson,
                             Model model){

        if(bindingResultSupplier.hasErrors()
                || bindingResultCriticality.hasErrors()
                || bindingResultContactInformation.hasErrors()
                || bindingResultAddress.hasErrors()
                || bindingResultContactPerson.hasErrors()){

            model.addAttribute("bindingResultSupplier", bindingResultSupplier);
            model.addAttribute("bindingResultCriticality", bindingResultCriticality);
            model.addAttribute("bindingResultContactInformation", bindingResultContactInformation);
            model.addAttribute("bindingResultAddress", bindingResultAddress);
            model.addAttribute("bindingResultContactPerson", bindingResultContactPerson);
            return true; }

        return false;
    }


    /**
     * This method checks, if the data base contains a supplier with the same name
     * and if this is the case, then it checks, if country, street name og postal district
     * also are the same.
     * @param supplier
     * @param address
     * @return supplier already exists or not
     */
    public boolean existAlready(Supplier supplier, Address address) {

        /*
          We need to compare following information:
          supplier name and country, street name og postal district from Address
          First we check, if we have the same name in data base.
         */

        String supplierName = supplier.getSupplierName();
        List<Supplier> alSuppliers = iSupplierRepository.findAll();
        Long supplierFoundId = null;
        boolean supplierNameFound = false;
        for (Supplier s : alSuppliers) {
            if (s.getSupplierName().equals(supplierName)) {
                supplierNameFound = true;
                supplierFoundId = s.getSupplierId();
                break;
            }
        }

        /*
          If supplierName is found in data base,
          we need check the address information connected to that supplier in data base.
          The id in address-tabel is the same ad supplier-id.
         */

        if (supplierNameFound) {

            String countryName = address.getCountry().getCountryName();
            String streetName = address.getStreetName();
            String postalDistrict = address.getPostalDistrict();

            Optional<Address> addressDB = iAddressRepository.findById(supplierFoundId);
            if (addressDB.isPresent()) {

                String countryNameDB = addressDB.get().getCountry().getCountryName();
                String streetNameDB = addressDB.get().getStreetName();
                String postalDistrictDB = addressDB.get().getPostalDistrict();

                return countryName.equals(countryNameDB)
                        && streetName.equals(streetNameDB)
                        && postalDistrict.equals(postalDistrictDB);
            }
        }
     return false;

    }


    /**
     * Returns the supplier with given id from data base.
     * If there is not any match, a EntityNotFoundException is thrown.
     * The double colon operator :: calls a method or constructor by referring to the class.
     * (class in this case: EntityNotFoundException)
     * Optional contains either Supplier or non-value, so this method can not throw NullPointerException
     * @param id that is given
     * @return Supplier with the given id
     */
    @Override
    public Supplier findById(Long id) {
        Optional<Supplier> supplier = iSupplierRepository.findById(id);
        return supplier.orElseThrow(() -> new EntityNotFoundException("Supplier with id " + id + " was not found"));
    }

    /**
     *  This method returns all suppliers. It is used, when controlling if the
     *  supplier already exists in data base.
     * @return all suppliers
     */
    @Override
    public List<Supplier> findAll() { return new ArrayList<>(iSupplierRepository.findAll()); }

    @Override
    public void deleteByID(Long id) { iSupplierRepository.deleteById(id);}
}


