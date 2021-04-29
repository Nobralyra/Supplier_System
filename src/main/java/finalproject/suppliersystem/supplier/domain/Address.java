package finalproject.suppliersystem.supplier.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String streetName;

    private String buildingNumber;

    private int postalCode;

    private String postalDistrict;

    private String country;

    //Child (owner)
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade =  CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    @MapsId
    private ContactInformation contactInformation;
}
