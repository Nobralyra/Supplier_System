package finalproject.suppliersystem.supplier.registration.registrationrepository;

import finalproject.suppliersystem.supplier.registration.domain.Criticality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICriticalityRepository extends JpaRepository<Criticality, Long> {
}
