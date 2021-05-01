package finalproject.suppliersystem.supplier.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortNatural;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * https://projectlombok.org/features/Data
 *
 * Validations:
 * https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-annotation-processor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier implements Comparable<Supplier>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @Column(length = 100)
    @Size(max = 100)
    @NotNull
    private String supplierName;

    @Column(length = 5)
    //dette skal genereres automatisk - nu bliver det ikke gjort
    // intellij brokker sig lige nu over disse annotationer, når man prøver at gemme
    //@Size(min = 5, max = 5)
    //@Positive
    //@NotNull
    private int supplierNumber;

    /**
     * Why use Set instead of List:
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     */
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "supplier_product_category",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id")
    )
    @SortNatural
    @UniqueElements
    private SortedSet<ProductCategory> productCategorySet = new TreeSet<>();

    /**
     * https://howtodoinjava.com/java/collections/java-comparable-interface/
     * @param o
     * @return
     */
    @Override
    public int compareTo(Supplier o)
    {
        return this.getSupplierId().compareTo( o.getSupplierId() );
    }
}
