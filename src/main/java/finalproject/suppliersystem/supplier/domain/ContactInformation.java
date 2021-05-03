package finalproject.suppliersystem.supplier.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private CountyCallingCode countyCallingCode;

    @Column(length = 20)
    @Size(max = 20)
    private String businessPhoneNumber;

    @Column(length = 100)
    @Size(max = 100, message = "Email must be between 0 and 100 characters")
    @Email(message = "Email should be valid")
    private String businessEmail;

    @Column(length = 100)
    @Size(max = 100, message = "URL must be between 0 and 100 characters")
    @URL(message = "URL should be valid")
    private String webpage;

    /**
     * ContactInformation child (owner) of Supplier
     * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


//    //mappedby is the field name
//    // https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
//    @OneToMany(
//            mappedBy = "contactInformation",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//            )
//    private List<ContactPerson> contactPersonList = new ArrayList<>();


}
