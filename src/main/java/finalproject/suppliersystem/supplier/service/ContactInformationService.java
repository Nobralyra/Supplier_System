package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.ContactInformation;
import finalproject.suppliersystem.supplier.repository.IContactInformationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactInformationService implements IService<ContactInformation> {

    private final IContactInformationRepository iContactInformationRepository;

    public ContactInformationService(IContactInformationRepository iContactInformationRepository) {
        this.iContactInformationRepository = iContactInformationRepository;
    }

    @Override
    public void save(ContactInformation contactInformation) {
        iContactInformationRepository.save(contactInformation);
    }

    @Override
    public ContactInformation findById(Long id) {
        Optional<ContactInformation> contactInformation = iContactInformationRepository.findById(id);
        return contactInformation.orElseThrow(() -> new EntityNotFoundException("Contact Information with id " + id + " was not found"));
    }

    @Override
    public List<ContactInformation> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void deleteByID(Long id) { iContactInformationRepository.deleteById(id);}
}
