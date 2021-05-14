package finalproject.suppliersystem.supplier.restapi.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CalculatorCriticalityRestService {

    /**
     * Criticality is a combination of Supplier Risk Level and Volume
     * Risk Level weights twice as much as Volume
     * See matrix in User Story 10
     * @param supplierRiskLevel
     * @param volume
     * @return
     */
    public Mono<String> calculateCriticality(Mono<String> supplierRiskLevel, Long volume){

        //There are 9 combinations of possibilities in matrix: 4 HIGH, 4 MEDIUM, 1 LOW

        Mono<String> answer = Mono.just("HIGH");

        boolean supplierRiskLevelLow = supplierRiskLevel.toString().equals("LOW");
        boolean supplierRiskLevelMedium = supplierRiskLevel.toString().equals("MEDIUM");
        boolean supplierRiskLevelHigh = supplierRiskLevel.toString().equals("HIGH");
        boolean volumeLow = volume <= 10000;
        boolean volumeMedium = volume <= 30000;
        boolean volumeHigh = volume > 30000;

        if(volumeLow & supplierRiskLevelLow) answer = Mono.just("LOW");

        // 4 combinations (1 + 1 + 2)
        if(volumeLow && supplierRiskLevelMedium
                ||volumeHigh && supplierRiskLevelLow
                ||volumeMedium && !supplierRiskLevelHigh){
            answer = Mono.just("MEDIUM");
        }

        //the rest 4 is HIGH
        return answer;
    }
}
