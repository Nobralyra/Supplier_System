package finalproject.suppliersystem.supplier.restapi.service;

import org.springframework.stereotype.Service;

@Service
public class AlgorithmSupplierRiskLevelService
{
    int convertedIssuesConcerningCooperation;
    /**
     * Corporate Social Responsibility* kategorier:
     * Low
     * Medium
     * High
     */

    /**
     * Availability Issues* kategorier:
     * Rarely (0-5 annually)
     * Sometimes (5-10 annually)
     * Often (10+ annually)
     */

    public void convertIssuesConcerningCooperation(int issuesConcerningCooperation)
    {
        /**
         * Spørg om der menes 0-5, 6-10 og 11 og op
         * Rarely (0-5 annually)
         * Sometimes (5-10 annually)
         * Often (10+ annually)
         */
        if(issuesConcerningCooperation < 6)
        {
            convertedIssuesConcerningCooperation = 1;
        }
        else if(issuesConcerningCooperation < 11)
        {
            convertedIssuesConcerningCooperation = 2;
            
        }
    }
}
