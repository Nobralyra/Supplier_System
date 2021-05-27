package finalproject.suppliersystem.supplier.registration.registrationservice;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.registration.domain.Supplier;
import finalproject.suppliersystem.supplier.registration.registrationrepository.IAddressRepository;
import finalproject.suppliersystem.supplier.registration.registrationrepository.ISupplierRepository;
import org.springframework.stereotype.Service;

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


