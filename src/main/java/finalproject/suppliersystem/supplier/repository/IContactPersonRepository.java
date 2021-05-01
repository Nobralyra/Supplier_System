package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactPersonRepository extends JpaRepository<ContactPerson, Long> {}
