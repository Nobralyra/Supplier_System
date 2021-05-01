package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.Address;
import finalproject.suppliersystem.supplier.repository.IAddressRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IService<Address> {

    private final IAddressRepository iAddressRepository;

    public AddressService(IAddressRepository iAddressRepository) {
        this.iAddressRepository = iAddressRepository;
    }

    @Override
    public void save(Address address) { iAddressRepository.save(address);}

    @Override
    public Address findById(Long id) {
        Optional<Address> address = iAddressRepository.findById(id);
        return address.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Address> findAll() { return new ArrayList<>(iAddressRepository.findAll());}

    @Override
    public void deleteByID(Long id) { iAddressRepository.deleteById(id);}
}
