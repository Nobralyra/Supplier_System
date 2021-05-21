package finalproject.suppliersystem.supplier.errorcheck;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface IHasError
{
    boolean hasErrors(BindingResult bindingResultSupplier,
                      BindingResult bindingResultCriticality,
                      BindingResult bindingResultContactInformation,
                      BindingResult bindingResultAddress,
                      BindingResult bindingResultContactPerson,
                      Model model);
}
