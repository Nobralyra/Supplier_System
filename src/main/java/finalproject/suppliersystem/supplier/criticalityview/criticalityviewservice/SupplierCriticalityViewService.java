package finalproject.suppliersystem.supplier.criticalityview.criticalityviewservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.criticalityview.view.SupplierCriticalityView;
import finalproject.suppliersystem.supplier.criticalityview.criticalityviewrepository.ISupplierCriticalityViewRepository;
import finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestservice.ICalculatorCriticalityRestService;
import finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestservice.ICalculatorSupplierRiskLevelRestService;
import finalproject.suppliersystem.supplier.registration.registrationservice.IProductCategoryService;
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
     * This method returns view data from supplier_criticality_view.
     * To set all fields in SupplierCriticalityView it is needed to loop each supplierCriticalityView and set
     * the missing fields:
     * Calculated Supplier Risk Level and Criticality
     * Find the product categories a supplier has relation too.
     *
     * @return List of SupplierCriticalityView
     */
    @Override
    public List<SupplierCriticalityView> findAll()
    {
        List<SupplierCriticalityView> supplierCriticalityViewList = iSupplierCriticalityViewRepository.findAll();
        for (SupplierCriticalityView supplierCriticalityView : supplierCriticalityViewList)
        {
            CategoryLevel getCalculatedSupplierRiskLevel = iCalculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(supplierCriticalityView.getCorporateSocialResponsibility(), supplierCriticalityView.getIssuesConcerningCooperation(), supplierCriticalityView.getAvailabilityIssues());
            CategoryLevel getCalculatedCriticality = iCalculatorCriticalityRestService.calculateCriticality(supplierCriticalityView.getVolume(), getCalculatedSupplierRiskLevel);
            supplierCriticalityView.setCalculatedSupplierRiskLevel(getCalculatedSupplierRiskLevel);
            supplierCriticalityView.setCalculatedCriticality(getCalculatedCriticality);

            List<String> productCategoryList = new ArrayList<>();

            productCategoryList.add(iProductCategoryService.findBySupplierSet_SupplierId(supplierCriticalityView.getSupplierId()).toString());

           supplierCriticalityView.setProductCategoryList(productCategoryList);
        }
        return supplierCriticalityViewList;
    }
}
