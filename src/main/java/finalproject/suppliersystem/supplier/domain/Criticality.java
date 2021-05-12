package finalproject.suppliersystem.supplier.domain;

import finalproject.suppliersystem.core.auditing.Audition;
import finalproject.suppliersystem.core.enums.CorporateSocialResponsibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * This class includes fields concerning categorizing of supplier by criticality
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Criticality extends Audition {

    /**
     * Criticality Id is the same as Supplier's id because there is used @MapsId in the @OneToOne relationship
     * between Criticality and Supplier
     */
    @Id
    private Long supplierId;

    @Column(length = 11)
    @NumberFormat(style= NumberFormat.Style.NUMBER)
    private Long volume;

    @Column(length = 10)
    @PositiveOrZero(message = "Issues has to be zero or a positive number")
    @NumberFormat(style= NumberFormat.Style.NUMBER)
    private int issuesConcerningCooperation;

    @Column(length = 6)
    @NotBlank(message = "Choose a responsibility level")
    private CorporateSocialResponsibility corporateSocialResponsibility;

    @Column(length = 10)
    @PositiveOrZero(message = "Issues has to be zero or a positive number")
    @NumberFormat(style= NumberFormat.Style.NUMBER)
    private int availibilityIssues;

    /**
     * child (owner) of Supplier
     * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
