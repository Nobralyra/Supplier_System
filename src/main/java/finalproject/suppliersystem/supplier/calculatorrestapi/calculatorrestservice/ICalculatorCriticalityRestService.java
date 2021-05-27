package finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;

public interface ICalculatorCriticalityRestService
{
    CategoryLevel calculateCriticality(Long volume, CategoryLevel supplierRiskLevel);

    CategoryLevel calculateVolumeLevel(Long volume);
}
