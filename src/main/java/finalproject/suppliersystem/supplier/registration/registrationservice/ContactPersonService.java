package finalproject.suppliersystem.supplier.registration.registrationservice;

import finalproject.suppliersystem.supplier.registration.domain.ContactPerson;
import finalproject.suppliersystem.supplier.registration.registrationrepository.IContactPersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactPersonService implements IContactPersonService
{
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

    /**
     * It is possible to register 2 contact persons to the same supplier.
     * This methos is used to find the first registred contactperson, when the
     * user wants to register two.
     * @param supplierId
     * @return
     */
    @Override
    public ContactPerson findBySupplierId(Long supplierId){

        List<ContactPerson> alContactPersons = findAll();

        for(ContactPerson c : alContactPersons){
            if(c.getContactInformation().getSupplierId().equals(supplierId)) return c;
        }
        return null;
    }
}
