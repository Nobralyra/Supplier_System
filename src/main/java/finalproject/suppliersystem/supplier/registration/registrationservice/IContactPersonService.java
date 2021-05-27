package finalproject.suppliersystem.supplier.registration.registrationservice;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.supplier.registration.domain.ContactPerson;

import java.util.List;

public interface IContactPersonService extends IService<ContactPerson>
{
    @Override
    void save(ContactPerson contactPerson);

    @Override
    ContactPerson findById(Long id);

    @Override
    List<ContactPerson> findAll();

    @Override
    void deleteByID(Long id);

    ContactPerson findBySupplierId(Long supplierId);
}
