package finalproject.suppliersystem.supplier.registration.domain;

import finalproject.suppliersystem.core.auditing.Audition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends Audition
{
    /**
     * Address Id is the same as Supplier's id because there is used @MapsId in the @OneToOne relationship
     * between ContactInformation and Supplier, and Address has an @OneToOne with ContactInformation
     */
    @Id
    private Long supplierId;

    @Column(length = 45)
    @Size(max = 45, message = "Street Name must be between 0 and 45 characters")
    private String streetName;

    @Column(length = 10)
    @Size(max = 10, message= "Building information must be between 0 and 10 characters")
    private String buildingInformation;

    @Column(length = 10)
    @PositiveOrZero(message = "Postal Code has to be a positive number")
    @NumberFormat(style= NumberFormat.Style.NUMBER)
    private int postalCode;

    @Column(length = 60)
    @Size(max = 60, message= "Postal District must be between 0 and 60 characters")
    private String postalDistrict;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    /**
     * Child (owner)
     * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "supplier_id")
    private ContactInformation contactInformation;

}
