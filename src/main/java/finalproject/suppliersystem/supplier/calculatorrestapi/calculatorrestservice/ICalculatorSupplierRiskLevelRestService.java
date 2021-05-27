package finalproject.suppliersystem.supplier.calculatorrestapi.calculatorrestservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;

public interface ICalculatorSupplierRiskLevelRestService
{
    CategoryLevel calculateSupplierRiskLevel(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues);
}
