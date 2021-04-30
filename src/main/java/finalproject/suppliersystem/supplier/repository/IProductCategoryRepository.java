package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long>
{
}
