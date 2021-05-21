package finalproject.suppliersystem.supplier.criticalityview.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.criticalityview.view.SupplierCriticalityView;
import finalproject.suppliersystem.supplier.criticalityview.repository.ISupplierProductCategoryCriticalityViewRepository;
import finalproject.suppliersystem.supplier.domain.ProductCategory;
import finalproject.suppliersystem.supplier.domain.Supplier;
import finalproject.suppliersystem.supplier.restapi.restservice.ICalculatorCriticalityRestService;
import finalproject.suppliersystem.supplier.restapi.restservice.ICalculatorSupplierRiskLevelRestService;
import finalproject.suppliersystem.supplier.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class SupplierProductCategoryCriticalityViewService implements ISupplierProductCategoryCriticalityViewService
{
    private final ISupplierProductCategoryCriticalityViewRepository iSupplierProductCategoryCriticalityViewRepository;
    private final ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService;
    private final ICalculatorCriticalityRestService iCalculatorCriticalityRestService;
    private final IService<Supplier> iSupplierService;
    private final IProductCategoryService iProductCategoryService;

    @Autowired
    public SupplierProductCategoryCriticalityViewService(ISupplierProductCategoryCriticalityViewRepository iSupplierProductCategoryCriticalityViewRepository,
                                                         ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService,
                                                         ICalculatorCriticalityRestService iCalculatorCriticalityRestService,
                                                         IService<Supplier> iSupplierService, IProductCategoryService iProductCategoryService)
    {
        this.iSupplierProductCategoryCriticalityViewRepository = iSupplierProductCategoryCriticalityViewRepository;
        this.iCalculatorSupplierRiskLevelRestService = iCalculatorSupplierRiskLevelRestService;
        this.iCalculatorCriticalityRestService = iCalculatorCriticalityRestService;
        this.iSupplierService = iSupplierService;
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
        Optional<SupplierCriticalityView> supplierProductCategoryCriticalityView = iSupplierProductCategoryCriticalityViewRepository.findById(id);
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
        supplierCriticalityViewArrayList = iSupplierProductCategoryCriticalityViewRepository.findAll();
        for (SupplierCriticalityView supplierCriticalityView : supplierCriticalityViewArrayList)
        {
            CategoryLevel getSupplierRiskLevel = iCalculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(supplierCriticalityView.getCorporateSocialResponsibility(), supplierCriticalityView.getIssuesConcerningCooperation(), supplierCriticalityView.getAvailabilityIssues());
            CategoryLevel getCriticality = iCalculatorCriticalityRestService.calculateCriticality(supplierCriticalityView.getVolume(), getSupplierRiskLevel);
            supplierCriticalityView.setSupplierRiskLevel(getSupplierRiskLevel);
            supplierCriticalityView.setCriticality(getCriticality);

            List<ProductCategory> productCategoryList = iProductCategoryService.findBySupplierSet_SupplierId(supplierCriticalityView.getSupplierId());
            supplierCriticalityView.setProductCategorySet(productCategoryList);

//            List<ProductCategory> test = new ArrayList<>();
//            test.add(iSupplierService.findById(supplierCriticalityView.getSupplierId()).getProductCategorySet().);
//            Set<ProductCategory> productCategoriesSet = iSupplierService.findById(supplierCriticalityView.getSupplierId()).getProductCategorySet();
//            supplierCriticalityView.setProductCategorySet(productCategoriesSet);
        }
        return supplierCriticalityViewArrayList;
    }
}
