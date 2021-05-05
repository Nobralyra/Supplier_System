package finalproject.suppliersystem.supplier.domain;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactInformation
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
    @Size(max = 100, message = "Email must be between 0 and 100 characters")
    @Email(message = "Email should be valid")
    private String businessEmail;

    @Column(length = 100)
    @Size(max = 100, message = "URL must be between 0 and 100 characters")
    @URL(message = "A valid URL shall contain a protocol (http or https) and a hostname. Example: https://example.com")
    private String webpage;

    /**
     * ContactInformation child (owner) of Supplier
     * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(table="contact_information", name = "supplier_id")
    private Supplier supplier;
}
