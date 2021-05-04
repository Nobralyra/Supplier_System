package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.Country;
import finalproject.suppliersystem.supplier.domain.CountryCallingCode;
import finalproject.suppliersystem.supplier.repository.ICountryRepository;
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
        return country.orElseThrow(EntityNotFoundException::new);
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
