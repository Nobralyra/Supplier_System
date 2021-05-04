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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactPersonId;

    @Column(length = 45)
    @Size(max = 45)
    private String nameContactPerson;


    @ManyToOne(targetEntity = CountryCallingCode.class, fetch = FetchType.LAZY)
    private CountryCallingCode countryCallingCode;

    @Column(length = 20)
    @Size(max = 20)
    private String phoneNumberContactPerson;

    @Column(length = 100)
    @Size(max = 100, message = "Email must be between 0 and 100 characters")
    @Email(message = "Email should be valid")
    private String emailContactPerson;

    //Child (owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = "contact_person", name = "supplier_id")
    @MapsId
    private ContactInformation contactInformation;
}
