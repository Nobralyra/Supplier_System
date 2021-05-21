package finalproject.suppliersystem.supplier.criticalityview.service;

import finalproject.suppliersystem.supplier.criticalityview.view.SupplierProductCategoryCriticalityView;

import java.util.List;

public interface ISupplierProductCategoryCriticalityViewService
{
    SupplierProductCategoryCriticalityView findById(Long id);

    List<SupplierProductCategoryCriticalityView> findAll();
}
