package finalproject.suppliersystem.supplier.controller;

import finalproject.suppliersystem.supplier.criticalityview.SupplierProductCategoryCriticalityView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OverviewCriticalityController
{
    @GetMapping("/supplier/criticality")
    public String showSupplierCriticality(SupplierProductCategoryCriticalityView supplierProductCategoryCriticalityView, Model model)
    {
        model.addAttribute("supplierProductCategoryCriticalityView", supplierProductCategoryCriticalityView);
        return "/supplier/criticality";
    }
}
