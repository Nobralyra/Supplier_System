package finalproject.suppliersystem.supplier.criticalityview;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierProductCategoryCriticalityViewService implements ISupplierProductCategoryCriticalityViewService
{
    private ISupplierProductCategoryCriticalityViewRepository iSupplierProductCategoryCriticalityViewRepository;

    @Autowired
    public SupplierProductCategoryCriticalityViewService(ISupplierProductCategoryCriticalityViewRepository iSupplierProductCategoryCriticalityViewRepository)
    {
        this.iSupplierProductCategoryCriticalityViewRepository = iSupplierProductCategoryCriticalityViewRepository;
    }

    /**
     * Returns the supplier with given id from data base.
     * If there is not any match, a EntityNotFoundException is thrown.
     * The double colon operator :: calls a method or constructor by referring to the class.
     * (class in this case: EntityNotFoundException)
     * Optional contains either Supplier or non-value, so this method can not throw NullPointerException
     * @param id that is given
     * @return Supplier with the given id
     */
    @Override
    public SupplierProductCategoryCriticalityView findById(Long id)
    {
        Optional<SupplierProductCategoryCriticalityView> supplierProductCategoryCriticalityView = iSupplierProductCategoryCriticalityViewRepository.findById(id);
        return supplierProductCategoryCriticalityView.orElseThrow(() -> new EntityNotFoundException("Supplier with id " + id + " was not found"));
    }

    /**
     *  This method returns all suppliers. It is used, when controlling if the
     *  supplier already exists in data base.
     * @return list of SupplierProductCategoryCriticalityView
     */
    @Override
    public List<SupplierProductCategoryCriticalityView> findAll()
    {
        return new ArrayList<>(iSupplierProductCategoryCriticalityViewRepository.findAll());
    }
}
