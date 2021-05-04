package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.Address;
import finalproject.suppliersystem.supplier.domain.ContactInformation;
import finalproject.suppliersystem.supplier.domain.Supplier;
import finalproject.suppliersystem.supplier.repository.IAddressRepository;
import finalproject.suppliersystem.supplier.repository.IContactInformationRepository;
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
    private final IContactInformationRepository iContactInformationRepository;

    public SupplierService(ISupplierRepository iSupplierRepository, IAddressRepository iAddressRepository, IContactInformationRepository iContactInformationRepository) {
        this.iSupplierRepository = iSupplierRepository;
        this.iAddressRepository = iAddressRepository;
        this.iContactInformationRepository = iContactInformationRepository;
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
     * @param bindingResultCountryCallingCode
     * @param bindingResultContactInformation
     * @param bindingResultAddress
     * @param bindingResultContactPerson
     * @return
     */
    public boolean hasErrors(BindingResult bindingResultSupplier,
                             BindingResult bindingResultContactInformation,
                             BindingResult bindingResultAddress,
                             BindingResult bindingResultContactPerson){
        return bindingResultSupplier.hasErrors()
                || bindingResultContactInformation.hasErrors()
                || bindingResultAddress.hasErrors()
                || bindingResultContactPerson.hasErrors();
    }


    /**
     * This method checks, if the data base contains a supplier with the same name
     * and if this is the case, then it checks, if country, street name og postal district
     * laso are the same.
     * @param supplier
     * @param address
     * @return supplier already exists or not
     */

    public boolean existAlready(Supplier supplier, Address address){

        boolean createdAlready = false;

        /*
          We need to compare following information:
          supplier name and country, street name og postal district from Address
          First we store this information about the supplier given as a parameter.
         */
        String supplierName = supplier.getSupplierName();
        //String countryName = address.getCountry();
        String streetname = address.getStreetName();
        String postalDistrict = address.getPostalDistrict();

        /*
          Do we have the same name in data base?
         */
        List<Supplier> alSuppliers = iSupplierRepository.findAll();
        Long supplierFoundId = null;
        boolean supplierNameFound = false;
        for(Supplier s : alSuppliers){
            if (s.getSupplierName().equals(supplierName)) {
                supplierNameFound = true;
                supplierFoundId = s.getSupplierId();
                break;
            }
        }

        /*
          If supplierName is found in data base,
          we need check the address information connected to that supplier in data base.
          Supplier-tabel is connected to ContactInformation-tabel, that has a field "supplier_id" (FK).
          Address-tabel has a field "contactInformationId" (FK) that is connected to ContactInformation
          by "contact_information_id".
         */

        if(supplierNameFound) {
            Optional<ContactInformation> contactInformationDB = iContactInformationRepository.findById(supplierFoundId);
            if(contactInformationDB.isPresent()){
                Long supplierIdIContactInformation = contactInformationDB.get().getSupplier().getSupplierId();

                Optional<Address> addressDB = iAddressRepository.findById(supplierIdIContactInformation);
                if(addressDB.isPresent()){
                    //String countryNameDB = addressDB.get().getCountry();
                    String streetnameDB = addressDB.get().getStreetName();
                    String postalDistrictDB = addressDB.get().getPostalDistrict();

//                    if(countryName.equals(countryNameDB)
//                            && streetname.equals(streetnameDB)
//                            && postalDistrict.equals(postalDistrictDB)) createdAlready = true;
                }
            }
        }
        return createdAlready;
    }


    /**
     * Returns the supplier with given id from data base.
     * If there is not any match, a EntityNotFoundException is thrown.
     * The double colon operator :: calls a method or constructor by referrring to the class.
     * (class in this case: EntityNotFoundException)
     * Optional contains either Supplier or non-value, so this method can not throw NullPointerException
     * @param id that is given
     * @return Supplier with the given id
     */

    @Override
    public Supplier findById(Long id) {
        Optional<Supplier> supplier = iSupplierRepository.findById(id);
        return supplier.orElseThrow(EntityNotFoundException::new);
    }

    /**
     *  This method returns all suppliers. It is used, when controlling if the
     *  supplier already exists in data base.
     * @return al suppliers
     */

    @Override
    public List<Supplier> findAll() { return new ArrayList<>(iSupplierRepository.findAll()); }

    @Override
    public void deleteByID(Long id) { iSupplierRepository.deleteById(id);}
}


