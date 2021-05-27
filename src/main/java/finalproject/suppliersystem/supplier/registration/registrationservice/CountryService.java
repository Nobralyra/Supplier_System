package finalproject.suppliersystem.supplier.registration.registrationservice;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.registration.domain.Country;
import finalproject.suppliersystem.supplier.registration.registrationrepository.ICountryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements IService<Country> {

    private final ICountryRepository iCountryRepository;

    public CountryService(ICountryRepository iCountryRepository) {
        this.iCountryRepository = iCountryRepository;
    }

    @Override
    public void save(Country country)
    {
        iCountryRepository.save(country);
    }

    @Override
    public Country findById(Long id)
    {
        Optional<Country> country = iCountryRepository.findById(id);
        return country.orElseThrow(() -> new EntityNotFoundException("Country with id " + id + " was not found"));
    }

    @Override
    public List<Country> findAll()
    {
        return new ArrayList<>(iCountryRepository.findAll());
    }

    @Override
    public void deleteByID(Long id)
    {
        iCountryRepository.deleteById(id);
    }

}
