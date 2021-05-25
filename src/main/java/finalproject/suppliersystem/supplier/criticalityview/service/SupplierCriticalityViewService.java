package finalproject.suppliersystem.supplier.criticalityview.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.criticalityview.view.SupplierCriticalityView;
import finalproject.suppliersystem.supplier.criticalityview.repository.ISupplierCriticalityViewRepository;
import finalproject.suppliersystem.supplier.domain.Supplier;
import finalproject.suppliersystem.supplier.restapi.restservice.ICalculatorCriticalityRestService;
import finalproject.suppliersystem.supplier.restapi.restservice.ICalculatorSupplierRiskLevelRestService;
import finalproject.suppliersystem.supplier.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class SupplierCriticalityViewService implements ISupplierCriticalityViewService
{
    private final ISupplierCriticalityViewRepository iSupplierCriticalityViewRepository;
    private final ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService;
    private final ICalculatorCriticalityRestService iCalculatorCriticalityRestService;
    private final IProductCategoryService iProductCategoryService;

    @Autowired
    public SupplierCriticalityViewService(ISupplierCriticalityViewRepository iSupplierCriticalityViewRepository,
                                          ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService,
                                          ICalculatorCriticalityRestService iCalculatorCriticalityRestService,
                                          IProductCategoryService iProductCategoryService)
    {
        this.iSupplierCriticalityViewRepository = iSupplierCriticalityViewRepository;
        this.iCalculatorSupplierRiskLevelRestService = iCalculatorSupplierRiskLevelRestService;
        this.iCalculatorCriticalityRestService = iCalculatorCriticalityRestService;
        this.iProductCategoryService = iProductCategoryService;
    }

    /**
     * Returns the supplier with given id from data base.
     * If there is not any match, a EntityNotFoundException is thrown.
     * The double colon operator :: calls a method or constructor by referring to the class.
     * (class in this case: EntityNotFoundException)
     * Optional contains either Supplier or non-value, so this method can not throw NullPointerException
     *
     * @param id that is given
     * @return Supplier with the given id
     */
    @Override
    public SupplierCriticalityView findById(Long id)
    {
        Optional<SupplierCriticalityView> supplierProductCategoryCriticalityView = iSupplierCriticalityViewRepository.findById(id);
        return supplierProductCategoryCriticalityView.orElseThrow(() -> new EntityNotFoundException("Supplier with id " + id + " was not found"));
    }

    /**
     * This method returns all suppliers. It is used, when controlling if the
     * supplier already exists in data base.
     *
     * @return list of SupplierCriticalityView
     */
    @Override
    public List<SupplierCriticalityView> findAll()
    {
        List<SupplierCriticalityView> supplierCriticalityViewArrayList;
        supplierCriticalityViewArrayList = iSupplierCriticalityViewRepository.findAll();
        for (SupplierCriticalityView supplierCriticalityView : supplierCriticalityViewArrayList)
        {
            CategoryLevel getSupplierRiskLevel = iCalculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(supplierCriticalityView.getCorporateSocialResponsibility(), supplierCriticalityView.getIssuesConcerningCooperation(), supplierCriticalityView.getAvailabilityIssues());
            CategoryLevel getCriticality = iCalculatorCriticalityRestService.calculateCriticality(supplierCriticalityView.getVolume(), getSupplierRiskLevel);
            supplierCriticalityView.setSupplierRiskLevel(getSupplierRiskLevel);
            supplierCriticalityView.setCriticality(getCriticality);

            List<String> productCategoryList = new ArrayList<>();

            productCategoryList.add(iProductCategoryService.findBySupplierSet_SupplierId(supplierCriticalityView.getSupplierId()).toString());

           supplierCriticalityView.setProductCategoryList(productCategoryList);
        }
        return supplierCriticalityViewArrayList;
    }
}
