package finalproject.suppliersystem.supplier.registration.registrationrepository;

import finalproject.suppliersystem.supplier.registration.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository creates the connection to our database.
 * If we use login with Spring Security, we can use method
 * findByUsename to load the user from database by username:
 * Optional<Supplier> findByUsername(String username);
 */

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Long> { }
