package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.CountyCallingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICallingCodeRepository extends JpaRepository<CountyCallingCode, Long>
{
}
