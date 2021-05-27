<<<<<<< HEAD:src/main/java/finalproject/suppliersystem/supplier/calculatorrestapi/restservice/ICalculatorSupplierRiskLevelRestService.java
package finalproject.suppliersystem.supplier.calculatorrestapi.restservice;
=======
package finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestservice;
>>>>>>> master:src/main/java/finalproject/suppliersystem/supplier/calculatorrestapi/calculatorrestservice/ICalculatorSupplierRiskLevelRestService.java

import finalproject.suppliersystem.core.enums.CategoryLevel;

public interface ICalculatorSupplierRiskLevelRestService
{
    CategoryLevel calculateSupplierRiskLevel(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues);
}
