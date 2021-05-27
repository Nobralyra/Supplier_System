package finalproject.suppliersystem.supplier.registration.registrationservice;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.registration.domain.Criticality;
import finalproject.suppliersystem.supplier.registration.registrationrepository.ICriticalityRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CriticalityService implements IService<Criticality> {

    private final ICriticalityRepository iCriticalityRepository;

    public CriticalityService(ICriticalityRepository iCriticalityRepository) {
        this.iCriticalityRepository = iCriticalityRepository;
    }

    @Override
    public void save(Criticality criticality) {
        iCriticalityRepository.save(criticality);
    }

    @Override
    public Criticality findById(Long id) {
        Optional<Criticality> criticality = iCriticalityRepository.findById(id);
        return criticality.orElseThrow(() -> new EntityNotFoundException("Criticality with id " + id + " was not found"));
    }

    @Override
    public List<Criticality> findAll() {
        return new ArrayList<>(iCriticalityRepository.findAll());
    }

    @Override
    public void deleteByID(Long id) { iCriticalityRepository.deleteById(id);}

}
