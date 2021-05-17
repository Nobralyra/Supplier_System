package finalproject.suppliersystem.supplier.restapi.restcontroller;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.restapi.service.CalculatorCriticalityRestService;
import finalproject.suppliersystem.supplier.restapi.service.ICalculatorSupplierRiskLevelRestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorCriticalityRestController {

    private final CalculatorCriticalityRestService calculatorCriticalityRestService;
    private final ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService;

    public CalculatorCriticalityRestController(CalculatorCriticalityRestService calculatorCriticalityRestService, ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService) {
        this.calculatorCriticalityRestService = calculatorCriticalityRestService;
        this.iCalculatorSupplierRiskLevelRestService = iCalculatorSupplierRiskLevelRestService;
    }

    /**
     * This receives data with the XMLHttpRequest (XHR)-objekt from HTML sent by ajax call.
     * It calculates the criticality based on riskLevel and volume.
     * It returns the JSON and statusCode inside the ResponseEntity-object.
     *
     * XMLHttpRequest: https://www.w3schools.com/xml/xml_http.asp
     * Ajax: https://www.w3schools.com/js/js_ajax_http.asp
     *
     * @param corporateSocialResponsibility
     * @param issuesConcerningCooperation
     * @param availabilityIssues
     * @param volume
     * @return
     */
    @GetMapping(value = "/criticality_api", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Enum<CategoryLevel>> getCriticality(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues, Long volume)
    {
        Enum<CategoryLevel> riskLevel = iCalculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
        Enum<CategoryLevel> criticality = calculatorCriticalityRestService.calculateCriticality(riskLevel, volume);
        return new ResponseEntity<>(criticality, HttpStatus.OK);
    }

}
