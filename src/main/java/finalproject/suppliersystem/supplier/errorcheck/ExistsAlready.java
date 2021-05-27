package finalproject.suppliersystem.supplier.errorcheck;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.registration.domain.Address;
import finalproject.suppliersystem.supplier.registration.domain.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExistsAlready implements IExistsAlready
{
    private final IService<Supplier> iSupplierService;
    private final IService<Address> iAddressService;

    public ExistsAlready(IService<Supplier> iSupplierService, IService<Address> iAddressService)
    {
        this.iSupplierService = iSupplierService;
        this.iAddressService = iAddressService;
    }

    /**
     * This method checks, if the data base contains a supplier with the same name
     * and if this is the case, then it checks, if country, street name og postal district
     * also are the same.
     * @param supplier
     * @param address
     * @return supplier already exists or not
     */
    @Override
    public boolean existAlready(Supplier supplier, Address address) {

        /*
          We need to compare following information:
          supplier name and country, street name og postal district from Address
          First we check, if we have the same name in data base.
         */

        String supplierName = supplier.getSupplierName();
        List<Supplier> allSuppliers = iSupplierService.findAll();
        Long supplierFoundId = null;
        boolean supplierNameFound = false;
        for (Supplier s : allSuppliers) {
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

            Address addressDB = iAddressService.findById(supplierFoundId);
            String countryNameDB = addressDB.getCountry().getCountryName();
            String streetNameDB = addressDB.getStreetName();
            String postalDistrictDB = addressDB.getPostalDistrict();

            return countryName.equals(countryNameDB)
                    && streetName.equals(streetNameDB)
                    && postalDistrict.equals(postalDistrictDB);
        }
        return false;

    }
}
