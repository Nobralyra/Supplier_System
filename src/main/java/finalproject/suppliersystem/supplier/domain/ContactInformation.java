package finalproject.suppliersystem.supplier.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactInformationId;

    private String callingCode;

    private String businessPhoneNumber;

    private String businessEmail;

    private String webpage;

    /**
     * ContactInformation child (owner) of Supplier
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    //mappedby is the field name
    // https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    @OneToMany(
            mappedBy = "contactInformation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            )
    private List<ContactPerson> contactPersonList = new ArrayList<>();


}
