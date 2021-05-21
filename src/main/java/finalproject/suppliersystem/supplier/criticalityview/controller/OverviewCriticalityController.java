package finalproject.suppliersystem.supplier.criticalityview.controller;

import finalproject.suppliersystem.supplier.criticalityview.service.ISupplierCriticalityViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OverviewCriticalityController
{
    private final ISupplierCriticalityViewService iSupplierCriticalityViewService;

    @Autowired
    public OverviewCriticalityController(ISupplierCriticalityViewService iSupplierCriticalityViewService)
    {
        this.iSupplierCriticalityViewService = iSupplierCriticalityViewService;
    }

    @GetMapping("/supplier/criticality")
    public String showSupplierCriticality(Model model)
    {
        model.addAttribute("supplierCriticalityView", iSupplierCriticalityViewService.findAll());
        return "/supplier/criticality";
    }
}
