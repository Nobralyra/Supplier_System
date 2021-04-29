package finalproject.suppliersystem.supplier.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductCategory implements Comparable<ProductCategory>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;

    @Column(length = 45)
    @Size(max = 45)
    @NotNull
    private String productName;

    /**
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     */
    @ManyToMany(mappedBy = "productCategorySet")
    @SortNatural
    private SortedSet<Supplier> supplierSet = new TreeSet<>();

    /**
     * https://howtodoinjava.com/java/collections/java-comparable-interface/
     * @param o
     * @return
     */
    @Override
    public int compareTo(ProductCategory o)
    {
        return this.getProductCategoryId().compareTo( o.getProductCategoryId() );
    }
}
