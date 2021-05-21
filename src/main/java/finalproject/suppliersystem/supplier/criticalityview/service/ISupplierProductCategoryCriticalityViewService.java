package finalproject.suppliersystem.supplier.criticalityview;

import java.util.List;

public interface ISupplierProductCategoryCriticalityViewService
{
    SupplierProductCategoryCriticalityView findById(Long id);

    List<SupplierProductCategoryCriticalityView> findAll();
}
