package finalproject.suppliersystem.supplier.registration.registrationservice;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.registration.domain.ProductCategory;

import java.util.List;

public interface IProductCategoryService extends IService<ProductCategory>
{
    @Override
    void save(ProductCategory productCategory);

    @Override
    ProductCategory findById(Long id);

    @Override
    List<ProductCategory> findAll();

    List<ProductCategory> findBySupplierSet_SupplierId(Long supplierId);

    @Override
    void deleteByID(Long id);
}
