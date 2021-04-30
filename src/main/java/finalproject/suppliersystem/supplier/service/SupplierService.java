package finalproject.suppliersystem.supplier.service;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.domain.Supplier;
import finalproject.suppliersystem.supplier.repository.ISupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements IService<Supplier> {

    private final ISupplierRepository iSupplierRepository;

    public SupplierService(ISupplierRepository iSupplierRepository) {
        this.iSupplierRepository = iSupplierRepository;
    }

    /**
     * stores the given supplier in data base
     * @param supplier
     */
    @Override
    public void save(Supplier supplier) {
        iSupplierRepository.save(supplier);
    }

    public boolean hasErrors(BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("bindingResult", bindingResult);
            return true;
        }
        return false;
    }

    /*
    public boolean existAlready(List<Supplier> alSuppliers){
        boolean createdAlready = false;
        String brugernavn = "";
        for(User u : alUsers){
            if(u.getFirstName().equals(user.getFirstName())
                    && u.getLastName().equals(user.getLastName())
                    && u.getEmailAddress().equals(user.getEmailAddress())){
                createdAlready = true;
                brugernavn = u.getUsername();
                break;
            }
        }
        if(createdAlready){
            String created = "Brugeren er allerede oprettet med brugernavnet: " + brugernavn;
            model.addAttribute("created", created);
    }*/


    /**
     * Returns the supplier with given id from data base.
     * If there is not any match, a EntityNotFoundException is thrown.
     * The double colon operator :: calls a method or constructor by referrring to the class.
     * (class in this case: EntityNotFoundException)
     * Optional contains either Supplier or non-value, so this method can not throw NullPointerException
     * @param id that is given
     * @return Supplier with the given id
     */

    @Override
    public Supplier findById(Long id) {
        Optional<Supplier> supplier = iSupplierRepository.findById(id);
        return supplier.orElseThrow(EntityNotFoundException::new);
    }

    /**
     *  This method returns all suppliers. It is used, when controlling if the
     *  supplier already exists in data base.
     * @return al suppliers
     */

    @Override
    public List<Supplier> findAll() {
        return new ArrayList<>(iSupplierRepository.findAll());
    }

    @Override
    public void deleteByID(Long id) { iSupplierRepository.deleteById(id);}
}


