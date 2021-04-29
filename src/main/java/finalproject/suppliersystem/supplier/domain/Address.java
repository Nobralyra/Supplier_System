package finalproject.suppliersystem.supplier.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address
{
    /**
     * Address Id is the same as Supplier's id because there is used @MapsId in the @OneToOne relationship
     * between ContactInformation and Supplier, and Address has an @OneToOne with ContactInformation
     */
    @Id
    private Long addressId;

    private String streetName;

    private String buildingNumber;

    private int postalCode;

    private String postalDistrict;

    @NotNull
    private String country;

    //Child (owner)
    // https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "supplier_id")
    private ContactInformation contactInformation;
}
