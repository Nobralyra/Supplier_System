package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import org.springframework.stereotype.Service;

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
    public Enum<CategoryLevel> calculateCriticality(Enum<CategoryLevel> supplierRiskLevel, Long volume) {

        CategoryLevel answer = CategoryLevel.HIGH;

        //There are 9 combinations of possibilities in matrix

        boolean supplierRiskLevelLow = supplierRiskLevel.equals(CategoryLevel.LOW);
        boolean supplierRiskLevelMedium = supplierRiskLevel.equals(CategoryLevel.MEDIUM);
        boolean supplierRiskLevelHigh = supplierRiskLevel.equals(CategoryLevel.HIGH);
        boolean volumeLow = volume >= 0 && volume <= 10000L;
        boolean volumeMedium = volume > 10000L && volume <= 30000L;
        boolean volumeHigh = volume > 30000L;

        if (volumeLow) {
            if (supplierRiskLevelLow) answer = CategoryLevel.LOW;
            if (supplierRiskLevelMedium) answer = CategoryLevel.MEDIUM;}

        if (volumeMedium) {
            if (!supplierRiskLevelHigh) answer = CategoryLevel.MEDIUM; }

        if (volumeHigh) {
            if (supplierRiskLevelLow) answer = CategoryLevel.MEDIUM;}

        return answer;
    }
}
