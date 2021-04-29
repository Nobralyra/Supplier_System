package finalproject.suppliersystem.supplier.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ContactInformation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactInformationId;

    private String callingCode;

    private String businessPhoneNumber;

    private String businessEmail;

    private String webpage;

    //ContactInformation is parent of Supplier
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "supplier")
    private Supplier supplier;

    //ContactInformation is parent of address
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "address")
    private Address address;

    //mappedby is the tablename
    // https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    @OneToMany(
            mappedBy = "contact_information",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            )
    private List<ContactPerson> contactPersonList;
}
