package finalproject.suppliersystem.supplier.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactPerson
{
    /**
     * Address Id is the same as Supplier's id because there is used @MapsId in the @OneToOne relationship
     * between ContactInformation and Supplier, and Address has an @OneToOne with ContactInformation
     */
    @Id
    private Long contactPersonId;

    private String nameContactPerson;

    private String emailContactPerson;

    private String phoneNumberContactPerson;

    //Child (owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @MapsId
    private ContactInformation contactInformation;
}
