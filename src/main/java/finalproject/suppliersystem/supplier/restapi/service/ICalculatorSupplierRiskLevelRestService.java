package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CorporateSocialResponsibility;
import reactor.core.publisher.Mono;

public interface ICalculatorSupplierRiskLevelRestService
{
    Mono<String> calculateSupplierRiskLevel(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues);
}
