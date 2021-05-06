package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.ContactPerson;
import finalproject.suppliersystem.supplier.repository.IContactPersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactPersonService implements IService<ContactPerson> {

    private final IContactPersonRepository iContactPersonRepository;

    public ContactPersonService(IContactPersonRepository iContactPersonRepository) {
        this.iContactPersonRepository = iContactPersonRepository;
    }

    @Override
    public void save(ContactPerson contactPerson) {
        iContactPersonRepository.save(contactPerson);
    }

    @Override
    public ContactPerson findById(Long id) {
        Optional<ContactPerson> contactPerson = iContactPersonRepository.findById(id);
        return contactPerson.orElseThrow(() -> new EntityNotFoundException("Contact Person with id " + id + " was not found"));
    }

    @Override
    public List<ContactPerson> findAll() {
        return new ArrayList<>(iContactPersonRepository.findAll());
    }

    @Override
    public void deleteByID(Long id) {
            iContactPersonRepository.deleteById(id);
    }
}
