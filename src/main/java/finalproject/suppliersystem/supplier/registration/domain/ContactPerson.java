package finalproject.suppliersystem.supplier.registration.domain;

import finalproject.suppliersystem.core.auditing.Audition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactPerson extends Audition
{
    /**
     * ContactPerson has to have it's one id, because the supplier_id is unique,
     * but we need to be able to store two contact persons. So the supplier_id can't be PK in this table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactPersonId;

    @Column(length = 45)
    @Size(max = 45, message = "Name must be between 0 and 45 characters")
    private String nameContactPerson;

    @Column(length = 30)
    @Size(max = 30, message = "Phone number must be between 0 and 30 characters")
    private String phoneNumberContactPerson;

    @Column(length = 100)
    @Size(max = 100, message = "Email must be between 0 and 100 characters")
    @Email(message = "Email should be valid")
    private String emailContactPerson;

    /**
     * Child (owner)
     * https://vladmihalcea.com/manytoone-jpa-hibernate/
     */

    @ManyToOne (fetch = FetchType.LAZY)
    private ContactInformation contactInformation;
}
