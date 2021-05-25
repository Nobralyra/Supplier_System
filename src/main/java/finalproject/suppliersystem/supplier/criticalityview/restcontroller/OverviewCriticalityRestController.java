package finalproject.suppliersystem.supplier.criticalityview.restcontroller;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.suppliersystem.supplier.criticalityview.service.ISupplierCriticalityViewService;
import finalproject.suppliersystem.supplier.criticalityview.view.SupplierCriticalityView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @RestController is a specialized version of the Controller. It's a REST style excelcontroller
 * Handles methods that shall return JSON/XML response directly to the client
 * Has @Controller and @ResponseBody
 * https://howtodoinjava.com/spring-boot2/rest/controller-restcontroller/
 */
@RestController
public class OverviewCriticalityRestController
{
    private final ISupplierCriticalityViewService iSupplierCriticalityViewService;

    @Autowired
    public OverviewCriticalityRestController(ISupplierCriticalityViewService iSupplierCriticalityViewService)
    {
        this.iSupplierCriticalityViewService = iSupplierCriticalityViewService;
    }

    /**
     * Gets executed when a request that matches the url specified in the @GetMapping.
     * https://www.baeldung.com/jackson-vs-gson
     * @return String
     */
    @GetMapping(value = "/supplier_criticality_table_api", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSupplierCriticalityData()
    {
        List<SupplierCriticalityView> supplierCriticalityViewList =  iSupplierCriticalityViewService.findAll();
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        String newJsonData = "Data";
        try
        {
            newJsonData = objectMapper.writeValueAsString(supplierCriticalityViewList);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            System.err.println(e);
        }

        return newJsonData;
    }
}
