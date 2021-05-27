package finalproject.suppliersystem.supplier.criticalityview.criticalityviewrestcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.suppliersystem.supplier.criticalityview.criticalityviewservice.ISupplierCriticalityViewService;
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
     * @return criticalityTableJsonData that is the data to get displayed
     */
    @GetMapping(value = "/supplier_criticality_table_api", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSupplierCriticalityData()
    {
        List<SupplierCriticalityView> supplierCriticalityViewList =  iSupplierCriticalityViewService.findAll();
        String criticalityTableJsonData = getJsonData(supplierCriticalityViewList);

        return criticalityTableJsonData;
    }

    /**
     * Convert the List of SupplierCriticalityView to JSON with Jackson
     * ObjectMapper is the Jackson serializer/deserializer
     * https://www.baeldung.com/jackson-vs-gson
     * @param supplierCriticalityViewList the given List of SupplierCriticalityView
     * @return jsonData the List object to JSON
     */
    private String getJsonData(List<SupplierCriticalityView> supplierCriticalityViewList)
    {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = "";
        try
        {
            // Generates JSON from a Java object and return the as a String
            // https://www.baeldung.com/jackson-object-mapper-tutorial
            jsonData = objectMapper.writeValueAsString(supplierCriticalityViewList);
        }
        catch (JsonProcessingException jsonProcessingException)
        {
            jsonProcessingException.printStackTrace();
        }

        return jsonData;
    }
}
