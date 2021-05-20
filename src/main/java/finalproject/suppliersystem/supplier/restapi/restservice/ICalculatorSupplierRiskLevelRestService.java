package finalproject.suppliersystem.supplier.restapi.restservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;

public interface ICalculatorSupplierRiskLevelRestService
{
    Enum<CategoryLevel> calculateSupplierRiskLevel(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues);
}
