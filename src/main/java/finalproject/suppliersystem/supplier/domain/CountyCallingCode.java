package finalproject.suppliersystem.supplier.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CallingCode
{
    @Id
    @Column(length = 7)
    @Size(max = 7)
    private String callingCode;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private ContactInformation contactInformation;

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
