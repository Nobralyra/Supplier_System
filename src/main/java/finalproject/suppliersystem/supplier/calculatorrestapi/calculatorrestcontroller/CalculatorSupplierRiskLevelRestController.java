package finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestcontroller;


import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestservice.ICalculatorSupplierRiskLevelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController is a specialized version of the Controller. It's a REST style excelcontroller
 * Handles methods that shall return JSON/XML response directly to the client
 * Has @Controller and @ResponseBody
 * https://howtodoinjava.com/spring-boot2/rest/controller-restcontroller/
 */
@RestController
public class CalculatorSupplierRiskLevelRestController
{
    private final ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService;

    /**
     * Constructor injection
     * We do not have to specify @Autowired, as long as the class only have one constructor and the class itself
     * is annotated with a Spring bean, because Spring automatic make the dependency to be injected via the constructor.
     * It is used here just for readability
     * <p>
     * To understand how constructor injection works:
     * https://stackoverflow.com/questions/40620000/spring-autowire-on-properties-vs-constructor
     * https://reflectoring.io/constructor-injection/
     *
     * @param iCalculatorSupplierRiskLevelRestService - interface of provided method
     */
    @Autowired
    public CalculatorSupplierRiskLevelRestController(ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService)
    {
        this.iCalculatorSupplierRiskLevelRestService = iCalculatorSupplierRiskLevelRestService;
    }

    /**
     * This receives data with the XMLHttpRequest (XHR)-objekt from HTML sent by ajax call.
     * It calculates the criticality based on riskLevel and volume.
     * It returns the JSON and statusCode inside the ResponseEntity-object.
     * <p>
     * XMLHttpRequest: https://www.w3schools.com/xml/xml_http.asp
     * Ajax: https://www.w3schools.com/js/js_ajax_http.asp
     *
     * @param corporateSocialResponsibility value of CategoryLevel
     * @param issuesConcerningCooperation   number of occurred Issues Concerning Cooperation with the supplier
     * @param availabilityIssues            number of occurred Availability Issues with the supplier
     * @return set the body, status, and headers of an HTTP response
     */
    @GetMapping(value = "/supplier_risk_level_api", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryLevel> getSupplierRiskLevel(@RequestParam CategoryLevel corporateSocialResponsibility, @RequestParam int issuesConcerningCooperation, @RequestParam int availabilityIssues)
    {
        CategoryLevel result = iCalculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
