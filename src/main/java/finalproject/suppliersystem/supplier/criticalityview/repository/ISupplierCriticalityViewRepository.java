package finalproject.suppliersystem.supplier.criticalityview.repository;

import finalproject.suppliersystem.supplier.criticalityview.view.SupplierCriticalityView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierCriticalityViewRepository extends JpaRepository<SupplierCriticalityView, Long>
{
}
