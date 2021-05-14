package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;

public interface ICalculatorSupplierRiskLevelRestService
{
    Enum<CategoryLevel> calculateSupplierRiskLevel(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues);
}
