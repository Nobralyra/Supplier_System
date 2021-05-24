package finalproject.suppliersystem.supplier.criticalityview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.suppliersystem.supplier.criticalityview.service.ISupplierCriticalityViewService;
import finalproject.suppliersystem.supplier.criticalityview.view.SupplierCriticalityView;

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
        return "/supplier/criticality";
    }
}
