package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.CountyCallingCode;
import finalproject.suppliersystem.supplier.repository.ICountryCallingCodeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryCallingCodeService implements IService<CountyCallingCode>
{
    private final ICountryCallingCodeRepository iCountryCallingCodeRepository;

    public CountryCallingCodeService(ICountryCallingCodeRepository iCountryCallingCodeRepository)
    {
        this.iCountryCallingCodeRepository = iCountryCallingCodeRepository;
    }

    @Override
    public void save(CountyCallingCode element)
    {
        iCountryCallingCodeRepository.save(element);
    }

    @Override
    public CountyCallingCode findById(Long id)
    {
        Optional<CountyCallingCode> callingCode = iCountryCallingCodeRepository.findById(id);
        return callingCode.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CountyCallingCode> findAll()
    {
        return new ArrayList<>(iCountryCallingCodeRepository.findAll());
    }

    @Override
    public void deleteByID(Long id)
    {
        iCountryCallingCodeRepository.deleteById(id);
    }
}
