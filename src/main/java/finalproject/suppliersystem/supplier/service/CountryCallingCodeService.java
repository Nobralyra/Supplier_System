package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.CountryCallingCode;
import finalproject.suppliersystem.supplier.repository.ICountryCallingCodeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryCallingCodeService implements IService<CountryCallingCode>
{
    private final ICountryCallingCodeRepository iCountryCallingCodeRepository;

    public CountryCallingCodeService(ICountryCallingCodeRepository iCountryCallingCodeRepository)
    {
        this.iCountryCallingCodeRepository = iCountryCallingCodeRepository;
    }

    @Override
    public void save(CountryCallingCode element)
    {
        iCountryCallingCodeRepository.save(element);
    }

    @Override
    public CountryCallingCode findById(Long id)
    {
        Optional<CountryCallingCode> callingCode = iCountryCallingCodeRepository.findById(id);
        return callingCode.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CountryCallingCode> findAll()
    {
        return new ArrayList<>(iCountryCallingCodeRepository.findAll());
    }

    @Override
    public void deleteByID(Long id)
    {
        iCountryCallingCodeRepository.deleteById(id);
    }
}
