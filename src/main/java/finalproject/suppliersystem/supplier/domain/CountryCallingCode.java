package finalproject.suppliersystem.supplier.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CountryCallingCode
{
    @Id
    @Column(length = 7)
    @Size(max = 7)
    private String callingCode;

    //to nedenstående kun her:

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private ContactInformation contactInformation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ContactPerson contactperson;

//    @OneToMany(
//            mappedBy = "callingCode",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<ContactPerson> contactPersonList = new ArrayList<>();
//
//    @OneToMany(
//            mappedBy = "callingCode",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<ContactInformation> contactInformationList = new ArrayList<>();
}
