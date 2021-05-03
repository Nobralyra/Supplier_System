package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.CountyCallingCode;
import finalproject.suppliersystem.supplier.repository.ICallingCodeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CallingCodeService implements IService<CountyCallingCode>
{
    private final ICallingCodeRepository iCallingCodeRepository;

    public CallingCodeService(ICallingCodeRepository iCallingCodeRepository)
    {
        this.iCallingCodeRepository = iCallingCodeRepository;
    }

    @Override
    public void save(CountyCallingCode element)
    {
        iCallingCodeRepository.save(element);
    }

    @Override
    public CountyCallingCode findById(Long id)
    {
        Optional<CountyCallingCode> callingCode = iCallingCodeRepository.findById(id);
        return callingCode.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CountyCallingCode> findAll()
    {
        return new ArrayList<>(iCallingCodeRepository.findAll());
    }

    @Override
    public void deleteByID(Long id)
    {
        iCallingCodeRepository.deleteById(id);
    }
}
