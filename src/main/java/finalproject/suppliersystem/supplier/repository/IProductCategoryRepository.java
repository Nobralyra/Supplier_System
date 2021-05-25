package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long>
{
    List<ProductCategory> findBySupplierSet_SupplierId(Long supplierId);
}
