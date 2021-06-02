package finalproject.suppliersystem.supplier.registration.domain;

import finalproject.suppliersystem.core.auditing.Audition;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactInformation extends Audition
{
    /**
     * Contact Information Id is the same as Supplier's id because there is used @MapsId in the @OneToOne relationship
     * between ContactInformation and Supplier
     */
    @Id
    private Long supplierId;

    @Column(length = 20)
    @Size(max = 30, message = "Phone number must be between 0 and 30 characters")
    private String businessPhoneNumber;

    @Column(length = 100)
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be between 0 and 100 characters")
    private String businessEmail;

    @Column(length = 100)
    @URL(message = "A valid URL shall contain a protocol (http or https) and a hostname. Example: https://example.com")
    @Size(max = 100, message = "URL must be between 0 and 100 characters")
    private String webpage;

    /**
     * ContactInformation child (owner) of Supplier
     * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(table="contact_information", name = "supplier_id")
    private Supplier supplier;

    /**
     * This guarantees they are not any orphan-address
     * (address refers to PK in ContactInformation)
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    /**
     * This guarantees they are not any orphan-contactPersons
     * (contactPerson refers to PK in ContactInformation)
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ContactPerson> contactPersons;

}
