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
     *
     * @param convertCategory what needs to be converted
     * @return result of the conversion
     */
    private int convertIssuesConcerningCooperationAndAvailabilityIssues(int convertCategory)
    {
        // Rarely
        if (convertCategory < 6)
        {
            return 1;
        }
        // Sometimes
        else if (convertCategory < 11)
        {
            return 2;
        }
        // Often
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
     *
     * @param convertCategory what needs to be converted
     * @return result of the conversion
     */
    private int convertCorporateSocialResponsibility(String convertCategory)
    {
        // Low
        if (convertCategory.equals("CORPORATE_SOCIAL_RESPONSIBILITY_LOW"))
        {
            return 2;
        }
        // Medium
        else if (convertCategory.equals("CORPORATE_SOCIAL_RESPONSIBILITY_MEDIUM"))
        {
            return 4;
        }
        // High
        else
        {
            return 6;
        }
    }

    /**
     * TODO: Check if sum of the Risk Level is better to use (is 18 calculations), then to check each number of each value
     * TODO: Check if a combination of those two a better
     */
    /**
     * CorporateSocialResponsibility:
     * 2 = Low
     * 4 = Medium
     * 6 = High
     *
     * Issues Concerning Cooperation and Availability Issues:
     * 1 = Low
     * 2 = Medium
     * 3 = High
     *
     * Link to where matrix is shown: https://docs.google.com/spreadsheets/d/1WUrAZfCbLKgNF_cWvrtBIctzHUmx0n9QSf9eGE1Mg0s/edit?usp=sharing
     *
     * @param corporateSocialResponsibility enum
     * @param issuesConcerningCooperation number of occurred Issues Concerning Cooperation with the supplier
     * @param availabilityIssues number of occurred Availability Issues with the supplier
     * @return result of Supplier Risk Level
     */
    public String calculateSupplierRiskLevel(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        int convertedCorporateSocialResponsibility = convertCorporateSocialResponsibility(corporateSocialResponsibility.name());
        int convertedIssuesConcerningCooperation = convertIssuesConcerningCooperationAndAvailabilityIssues(issuesConcerningCooperation);
        int convertedAvailabilityIssues = convertIssuesConcerningCooperationAndAvailabilityIssues(availabilityIssues);

        // Risk Level can never be lower level than what level Corporate Social Responsibility is
        // Is removing 7 out of the 27 possibilities
        if (convertedCorporateSocialResponsibility == 6)
        {
            return "High";
        }

        // First call summationSupplierRiskLevel after convertedCorporateSocialResponsibility has been check of being 6,
        // so we do not use unnecessary resources and time
        int sum = summationSupplierRiskLevel(convertedCorporateSocialResponsibility, convertedIssuesConcerningCooperation, convertedAvailabilityIssues);

        // There is one High that we de not catch before, but can be check with sum
        if (sum == 10)
        {
            return "High";
        }
        // If sum is bigger than 6 and smaller than 10 (does not need to check that, because they would had been returned already
        if (sum > 6)
        {
            return "Medium";
        }
        // Has one possibility where sum is 6, but the correct return is Low, but it can be checked if convertedIssuesConcerningCooperation and convertedAvailabilityIssues is 2
        if (convertedIssuesConcerningCooperation == 2 && convertedAvailabilityIssues == 2)
        {
            return "Low";
        }
        // The rest of where sum is 6 should return Medidum
        if (sum == 6)
        {
            return "Medium";
        }

        // The rest possibilities that did not match any of the if statements is always a Low
        return "Low";
    }

    /**
     * Calculates the sum of convertedCorporateSocialResponsibility, convertedIssuesConcerningCooperation and convertedAvailabilityIssues
     * @param convertedCorporateSocialResponsibility converted number
     * @param convertedIssuesConcerningCooperation converted number
     * @param convertedAvailabilityIssues converted number
     * @return sum of the total
     */
    private int summationSupplierRiskLevel(int convertedCorporateSocialResponsibility, int convertedIssuesConcerningCooperation, int convertedAvailabilityIssues)
    {
        return convertedCorporateSocialResponsibility + convertedIssuesConcerningCooperation + convertedAvailabilityIssues;
    }
}
