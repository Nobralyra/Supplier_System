package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import org.springframework.stereotype.Service;

@Service
public class CalculatorCriticalityRestService {

    /**
     * Criticality is a combination of Supplier Risk Level and Volume
     * Risk Level weights twice as much as Volume
     * See matrix in User Story 10
     * @param volume
     * @param supplierRiskLevel ENUM of Supplier Risk Level
     * @return ENUM result of Supplier Risk Level
     */
    public Enum<CategoryLevel> calculateCriticality(Long volume, Enum<CategoryLevel> supplierRiskLevel) {

        CategoryLevel answer = CategoryLevel.HIGH;

        //There are 9 combinations of possibilities in matrix

        boolean supplierRiskLevelLow = supplierRiskLevel.equals(CategoryLevel.LOW);
        boolean supplierRiskLevelMedium = supplierRiskLevel.equals(CategoryLevel.MEDIUM);
        boolean supplierRiskLevelHigh = supplierRiskLevel.equals(CategoryLevel.HIGH);
        Enum<CategoryLevel> volumeLevel = calculateVolumeLevel(volume);

        if (volumeLevel.equals(CategoryLevel.LOW)) {
            if (supplierRiskLevelLow) answer = CategoryLevel.LOW;
            if (supplierRiskLevelMedium) answer = CategoryLevel.MEDIUM;}

        if (volumeLevel.equals(CategoryLevel.MEDIUM))  {
            if (!supplierRiskLevelHigh) answer = CategoryLevel.MEDIUM; }

        if (volumeLevel.equals(CategoryLevel.HIGH))  {
            if (supplierRiskLevelLow) answer = CategoryLevel.MEDIUM;}

        return answer;
    }

    /**
     * returns the volume level as a CategoryLevel
     * @param volume
     * @return
     */
    public Enum<CategoryLevel> calculateVolumeLevel(Long volume){

        if(volume >= 0 && volume <= 10000L) return CategoryLevel.LOW;
        if(volume > 10000L && volume <= 30000L) return CategoryLevel.MEDIUM;
        else return CategoryLevel.HIGH;
    }
}