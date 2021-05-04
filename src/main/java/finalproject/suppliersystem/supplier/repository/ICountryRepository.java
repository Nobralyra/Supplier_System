package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {
}
