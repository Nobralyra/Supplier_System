package finalproject.suppliersystem.supplier.calculatorrestapi.restcontroller;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.calculatorrestapi.restservice.ICalculatorCriticalityRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorCriticalityRestController
{
    private final ICalculatorCriticalityRestService ICalculatorCriticalityRestService;

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
     * @param ICalculatorCriticalityRestService - interface of provided method
     */
    @Autowired
    public CalculatorCriticalityRestController(ICalculatorCriticalityRestService ICalculatorCriticalityRestService)
    {
        this.ICalculatorCriticalityRestService = ICalculatorCriticalityRestService;
    }

    /**
     * This receives data with the XMLHttpRequest (XHR)-objekt from HTML sent by ajax call.
     * It calculates the criticality based on riskLevel and volume.
     * It returns the JSON and statusCode inside the ResponseEntity-object.
     * <p>
     * XMLHttpRequest: https://www.w3schools.com/xml/xml_http.asp
     * Ajax: https://www.w3schools.com/js/js_ajax_http.asp
     *
     * @param volume
     * @param supplierRiskLevel ENUM of Supplier Risk Level
     * @return set the body, status, and headers of an HTTP response
     */
    @GetMapping(value = "/criticality_api", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryLevel> getCriticality(@RequestParam Long volume, @RequestParam CategoryLevel supplierRiskLevel)
    {
        CategoryLevel criticality = ICalculatorCriticalityRestService.calculateCriticality(volume, supplierRiskLevel);
        return new ResponseEntity<>(criticality, HttpStatus.OK);
    }

}
