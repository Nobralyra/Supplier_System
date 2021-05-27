package finalproject.suppliersystem.supplier.registration.registrationrepository;

import finalproject.suppliersystem.supplier.registration.domain.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactInformationRepository extends JpaRepository<ContactInformation, Long> {
}
