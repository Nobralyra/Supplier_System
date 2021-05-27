<<<<<<< HEAD:src/main/java/finalproject/suppliersystem/supplier/calculatorrestapi/restservice/ICalculatorCriticalityRestService.java
package finalproject.suppliersystem.supplier.calculatorrestapi.restservice;
=======
package finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestservice;
>>>>>>> master:src/main/java/finalproject/suppliersystem/supplier/calculatorrestapi/calculatorrestservice/ICalculatorCriticalityRestService.java

import finalproject.suppliersystem.core.enums.CategoryLevel;

public interface ICalculatorCriticalityRestService
{
    CategoryLevel calculateCriticality(Long volume, CategoryLevel supplierRiskLevel);

    CategoryLevel calculateVolumeLevel(Long volume);
}
