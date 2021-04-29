package finalproject.suppliersystem.supplier.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

    @Column(length = 45)
    @Size(max = 45)
    private String streetName;

    @Column(length = 10)
    @Size(max = 10)
    private String buildingNumber;

    @Column(length = 45)
    @Size(max = 45)
    private int postalCode;

    @Column(length = 60)
    @Size(max = 60)
    private String postalDistrict;

    @Column(length = 55)
    @Size(max = 55)
    @NotNull
    private String country;

    //Child (owner)
    // https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "supplier_id")
    private ContactInformation contactInformation;
}
