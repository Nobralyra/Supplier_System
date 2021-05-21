package finalproject.suppliersystem.supplier.criticalityview.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.criticalityview.view.SupplierProductCategoryCriticalityView;
import finalproject.suppliersystem.supplier.criticalityview.repository.ISupplierProductCategoryCriticalityViewRepository;
import finalproject.suppliersystem.supplier.restapi.restservice.ICalculatorCriticalityRestService;
import finalproject.suppliersystem.supplier.restapi.restservice.ICalculatorSupplierRiskLevelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierProductCategoryCriticalityViewService implements ISupplierProductCategoryCriticalityViewService
{
    private final ISupplierProductCategoryCriticalityViewRepository iSupplierProductCategoryCriticalityViewRepository;
    private final ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService;
    private final ICalculatorCriticalityRestService iCalculatorCriticalityRestService;

    @Autowired
    public SupplierProductCategoryCriticalityViewService(ISupplierProductCategoryCriticalityViewRepository iSupplierProductCategoryCriticalityViewRepository, ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService, ICalculatorCriticalityRestService iCalculatorCriticalityRestService)
    {
        this.iSupplierProductCategoryCriticalityViewRepository = iSupplierProductCategoryCriticalityViewRepository;
        this.iCalculatorSupplierRiskLevelRestService = iCalculatorSupplierRiskLevelRestService;
        this.iCalculatorCriticalityRestService = iCalculatorCriticalityRestService;
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
        List<SupplierProductCategoryCriticalityView> supplierProductCategoryCriticalityViewArrayList;
        supplierProductCategoryCriticalityViewArrayList = iSupplierProductCategoryCriticalityViewRepository.findAll();
        for (SupplierProductCategoryCriticalityView supplierProductCategoryCriticalityView: supplierProductCategoryCriticalityViewArrayList)
        {
            CategoryLevel getSupplierRiskLevel = iCalculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(supplierProductCategoryCriticalityView.getCorporateSocialResponsibility(), supplierProductCategoryCriticalityView.getIssuesConcerningCooperation(), supplierProductCategoryCriticalityView.getAvailabilityIssues());
            CategoryLevel getCriticality = iCalculatorCriticalityRestService.calculateCriticality(supplierProductCategoryCriticalityView.getVolume(), getSupplierRiskLevel);
            supplierProductCategoryCriticalityView.setSupplierRiskLevel(getSupplierRiskLevel);
            supplierProductCategoryCriticalityView.setCriticality(getCriticality);
        }
        return supplierProductCategoryCriticalityViewArrayList;
    }
}
