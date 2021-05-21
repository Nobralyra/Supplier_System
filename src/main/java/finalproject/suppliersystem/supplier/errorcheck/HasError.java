package finalproject.suppliersystem.supplier.errorcheck;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class HasError implements IHasError
{
    /**
     * Returns true or false for errors in validation
     *
     * @param bindingResultSupplier
     * @param bindingResultContactInformation
     * @param bindingResultAddress
     * @param bindingResultContactPerson
     * @return boolean
     */
    @Override
    public boolean hasErrors(BindingResult bindingResultSupplier,
                             BindingResult bindingResultCriticality,
                             BindingResult bindingResultContactInformation,
                             BindingResult bindingResultAddress,
                             BindingResult bindingResultContactPerson,
                             Model model){

        if(bindingResultSupplier.hasErrors()
                || bindingResultCriticality.hasErrors()
                || bindingResultContactInformation.hasErrors()
                || bindingResultAddress.hasErrors()
                || bindingResultContactPerson.hasErrors()){

            model.addAttribute("bindingResultSupplier", bindingResultSupplier);
            model.addAttribute("bindingResultCriticality", bindingResultCriticality);
            model.addAttribute("bindingResultContactInformation", bindingResultContactInformation);
            model.addAttribute("bindingResultAddress", bindingResultAddress);
            model.addAttribute("bindingResultContactPerson", bindingResultContactPerson);
            return true; }

        return false;
    }
}
