package finalproject.suppliersystem.supplier.criticalityview.controller;

import finalproject.suppliersystem.supplier.criticalityview.service.ISupplierProductCategoryCriticalityViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OverviewCriticalityController
{
    private final ISupplierProductCategoryCriticalityViewService iSupplierProductCategoryCriticalityViewService;

    @Autowired
    public OverviewCriticalityController(ISupplierProductCategoryCriticalityViewService iSupplierProductCategoryCriticalityViewService)
    {
        this.iSupplierProductCategoryCriticalityViewService = iSupplierProductCategoryCriticalityViewService;
    }

    @GetMapping("/supplier/criticality")
    public String showSupplierCriticality(Model model)
    {
        model.addAttribute("supplierProductCategoryCriticalityView", iSupplierProductCategoryCriticalityViewService.findAll());
        return "/supplier/criticality";
    }
}
