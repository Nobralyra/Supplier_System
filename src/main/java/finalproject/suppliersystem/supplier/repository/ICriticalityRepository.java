package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.Criticality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICriticalityRepository extends JpaRepository<Criticality, Long> {
}
