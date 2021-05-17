package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorSupplierRiskLevelRestService implements ICalculatorSupplierRiskLevelRestService
{
    @Autowired
    public CalculatorSupplierRiskLevelRestService()
    {
    }

    /**
     * IssuesConcerningCooperation categories:
     * Rarely (0-5 annually)
     * Sometimes (6-10 annually)
     * Often (10+ annually)
     *
     * @param convertCategory given value
     * @return the enum value
     */
    private CategoryLevel convertIssuesConcerningCooperation(int convertCategory)
    {
        // Rarely
        if (convertCategory < 6)
        {
            return CategoryLevel.LOW;
        }
        // Sometimes
        if (convertCategory < 11)
        {
            return CategoryLevel.MEDIUM;
        }
        // Often
        return CategoryLevel.HIGH;
    }

    /**
     * AvailabilityIssues categories:
     * Rarely (0-5 annually)
     * Sometimes (6-10 annually)
     * Often (10+ annually)
     *
     * @param convertCategory given value
     * @return the enum value
     */
    private CategoryLevel convertAvailabilityIssues(int convertCategory)
    {
        // Rarely
        if (convertCategory < 6)
        {
            return CategoryLevel.LOW;
        }
        // Sometimes
        if (convertCategory < 11)
        {
            return CategoryLevel.MEDIUM;
        }
        // Often
        return CategoryLevel.HIGH;
    }

    /**
     * Risk Level can never be lower level than what level corporateSocialResponsibility is:
     * corporateSocialResponsibility HIGH => HIGH always
     * corporateSocialResponsibility MEDIUM + convertedIssuesConcerningCooperation HIGH + convertedAvailabilityIssues HIGH => HIGH
     * corporateSocialResponsibility MEDIUM all other cases => MEDIUM
     * corporateSocialResponsibility LOW + convertedIssuesConcerningCooperation HIGH => MEDIUM
     * corporateSocialResponsibility LOW + convertedAvailabilityIssues HIGH => MEDIUM
     * All others cases => LOW
     *
     * Link to where matrix is shown: https://docs.google.com/spreadsheets/d/1WUrAZfCbLKgNF_cWvrtBIctzHUmx0n9QSf9eGE1Mg0s/edit?usp=sharing
     *
     * @param corporateSocialResponsibility value of CategoryLevel
     * @param issuesConcerningCooperation   number of occurred Issues Concerning Cooperation with the supplier
     * @param availabilityIssues            number of occurred Availability Issues with the supplier
     * @return ENUM result of Supplier Risk Level
     */
    @Override
    public Enum<CategoryLevel> calculateSupplierRiskLevel(CategoryLevel corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {
        CategoryLevel convertedIssuesConcerningCooperation = convertIssuesConcerningCooperation(issuesConcerningCooperation);
        CategoryLevel convertedAvailabilityIssues = convertAvailabilityIssues(availabilityIssues);

        if (corporateSocialResponsibility == CategoryLevel.HIGH)
        {
            return CategoryLevel.HIGH;
        }

        if (corporateSocialResponsibility == CategoryLevel.MEDIUM)
        {
            if (convertedIssuesConcerningCooperation == CategoryLevel.HIGH && convertedAvailabilityIssues == CategoryLevel.HIGH)
            {
                return CategoryLevel.HIGH;
            }
            return CategoryLevel.MEDIUM;
        }

        if (convertedIssuesConcerningCooperation == CategoryLevel.HIGH)
        {
            return CategoryLevel.MEDIUM;
        }

        if (convertedAvailabilityIssues == CategoryLevel.HIGH)
        {
            return CategoryLevel.MEDIUM;
        }

        // The rest possibilities that did not match any of the if statements is always LOW
        return CategoryLevel.LOW;
    }
}
