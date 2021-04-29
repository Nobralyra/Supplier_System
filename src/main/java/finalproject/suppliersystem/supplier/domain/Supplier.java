package finalproject.suppliersystem.supplier.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * https://projectlombok.org/features/Data
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

    @NotNull
    private String supplierName;

    @NotNull
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
