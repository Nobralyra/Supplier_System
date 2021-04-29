package finalproject.suppliersystem.supplier.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ContactPerson
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactPersonId;

    private String nameContactPerson;

    private String emailContactPerson;

    private String phoneNumberContactPerson;

    //Child (owner)
    @ManyToOne(fetch = FetchType.LAZY)
    private ContactInformation contactInformation;
}
