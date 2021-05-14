package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import reactor.core.publisher.Mono;

public interface ICalculatorSupplierRiskLevelRestService
{
    Mono<String> calculateSupplierRiskLevel(CategoryLevel categoryLevel, int issuesConcerningCooperation, int availabilityIssues);
}
