package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactInformationRepository extends JpaRepository<ContactInformation, Long> {
}
