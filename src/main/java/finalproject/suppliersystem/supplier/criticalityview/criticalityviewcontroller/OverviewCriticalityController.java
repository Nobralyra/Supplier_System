package finalproject.suppliersystem.supplier.criticalityview.criticalityviewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class OverviewCriticalityController
{
    @GetMapping("/supplier/criticality")
    public String showSupplierCriticality()
    {
        return "/supplier/criticality";
    }
}
