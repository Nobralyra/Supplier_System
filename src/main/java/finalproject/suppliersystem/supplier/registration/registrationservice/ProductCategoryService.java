package finalproject.suppliersystem.supplier.registration.registrationservice;

import finalproject.suppliersystem.supplier.registration.domain.ProductCategory;
import finalproject.suppliersystem.supplier.registration.registrationrepository.IProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ProductCategoryService implements IProductCategoryService
{
    private final IProductCategoryRepository iProductCategoryRepository;

    /**
     * Constructor injection
     * We do not have to specify @Autowired, as long as the class only have one constructor and the class itself
     * is annotated with a Spring bean, because Spring automatic make the dependency to be injected via the constructor.
     * It is used here just for readability
     *
     * To understand how constructor injection works:
     * https://stackoverflow.com/questions/40620000/spring-autowire-on-properties-vs-constructor
     * https://reflectoring.io/constructor-injection/
     *
     * @param iProductCategoryRepository - interface of provided methods
     */
    @Autowired
    public ProductCategoryService(IProductCategoryRepository iProductCategoryRepository)
    {
        this.iProductCategoryRepository = iProductCategoryRepository;
    }

    @Override
    public void save(ProductCategory productCategory)
    {
        iProductCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory findById(Long id)
    {
        Optional<ProductCategory> productCategory = iProductCategoryRepository.findById(id);
        return productCategory.orElseThrow(() -> new EntityNotFoundException("Product Category with id " + id + " was not found"));
    }

    @Override
    public List<ProductCategory> findAll()
    {
        return new ArrayList<>(iProductCategoryRepository.findAll());
    }

    @Override
    public List<ProductCategory> findBySupplierSet_SupplierId(Long supplierId)
    {
        List<ProductCategory> productCategoryList = iProductCategoryRepository.findBySupplierSet_SupplierId(supplierId);
        return productCategoryList;
    }

    @Override
    public void deleteByID(Long id)
    {
        iProductCategoryRepository.deleteById(id);
    }
}
