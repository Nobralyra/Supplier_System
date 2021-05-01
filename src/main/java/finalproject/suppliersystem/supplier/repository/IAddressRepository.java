package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {
}
