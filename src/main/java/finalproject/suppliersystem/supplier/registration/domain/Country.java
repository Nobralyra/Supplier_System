package finalproject.suppliersystem.supplier.registration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"countryName"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Column(length = 60)
    @NotBlank(message = "Please choose a country")
    @Size(max = 60, message =" Country name must be between 0 and 55 characters\"")
    private String countryName;
}
