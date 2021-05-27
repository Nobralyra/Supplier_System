package finalproject.suppliersystem.supplier.registration.registrationrepository;

import finalproject.suppliersystem.supplier.registration.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {
}
