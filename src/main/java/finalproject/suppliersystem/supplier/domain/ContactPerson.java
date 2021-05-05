package finalproject.suppliersystem.supplier.domain;

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
public class ContactPerson
{
    /**
     * Contact Person Id is the same as Supplier's id because there is used @MapsId in the @OneToOne relationship
     * between ContactInformation and Supplier, and there is used @MapsId in the @ManyToOne relationship
     * between ContactInformation and ContactPerson
     */
    @Id
    private Long contactPersonId;

    @Column(length = 45)
    @Size(max = 45, message = "Name must be between 0 and 45 characters")
    private String nameContactPerson;

    @Column(length = 20)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = "contact_person", name = "supplier_id")
    @MapsId
    private ContactInformation contactInformation;
}
