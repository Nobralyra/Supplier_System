package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CorporateSocialResponsibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmSupplierRiskLevelService
{
    @Autowired
    public AlgorithmSupplierRiskLevelService()
    {
    }

    /**
     * IssuesConcerningCooperation and AvailabilityIssues categories:
     * Rarely (0-5 annually)
     * Sometimes (6-10 annually)
     * Often (10+ annually)
     * @param convertCategory int
     * @return int
     */
    private int convertIssuesConcerningCooperationAndAvailabilityIssues(int convertCategory)
    {
        if(convertCategory < 6)
        {
            return 1;
        }
        else if(convertCategory < 11)
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }

    /**
     * Corporate Social Responsibility category:
     * Low
     * Medium
     * High
     * @param convertCategory String
     * @return int
     */
    private int convertCorporateSocialResponsibility(String convertCategory)
    {
        if(convertCategory.equals("CORPORATE_SOCIAL_RESPONSIBILITY_LOW"))
        {
            return 2;
        }
        else if(convertCategory.equals("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM"))
        {
            return 4;
        }
        else
        {
            return 6;
        }
    }

    public String calculateSupplierRiskLevel(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        int convertedCorporateSocialResponsibility = convertCorporateSocialResponsibility(corporateSocialResponsibility.name());
        int convertedIssuesConcerningCooperation = convertIssuesConcerningCooperationAndAvailabilityIssues(issuesConcerningCooperation);
        int convertedAvailabilityIssues = convertIssuesConcerningCooperationAndAvailabilityIssues(availabilityIssues);

        if (convertedAvailabilityIssues = 6)

    }
}
