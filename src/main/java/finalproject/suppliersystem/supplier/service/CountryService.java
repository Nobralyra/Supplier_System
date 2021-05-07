package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.Country;
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

    /**
     * Checks if unique country name already exists in the database, and if true then take its id and set it in country object
     * If findAll() size is 0 it goes straight to return country
     * @param country
     * @return Country
     */
    public Country checkUniqueCountryName(Country country)
    {
        List<Country> countryList = findAll();

        for (Country countryName: countryList)
        {
            if(country.getCountryName().equals(countryName.getCountryName()))
            {
                country.setCountryId(countryName.getCountryId());
                return country;
            }
        }
        return country;
    }
}
