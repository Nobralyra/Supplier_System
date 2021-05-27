package finalproject.suppliersystem.supplier.errorcheck;

import finalproject.suppliersystem.supplier.registration.domain.Address;
import finalproject.suppliersystem.supplier.registration.domain.Supplier;

public interface IExistsAlready
{
    boolean existAlready(Supplier supplier, Address address);
}
