package finalproject.suppliersystem.supplier.errorcheck;

import finalproject.suppliersystem.supplier.domain.Address;
import finalproject.suppliersystem.supplier.domain.Supplier;

public interface IExistsAlready
{
    boolean existAlready(Supplier supplier, Address address);
}
