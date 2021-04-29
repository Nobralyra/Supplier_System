package finalproject.suppliersystem.supplier.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.SortedSet;

/**
 * https://projectlombok.org/features/Data
 */
@Data
@Entity
public class Supplier
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String supplierName;

    private int supplierNumber;

    //Child (owner)
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade =  CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    @MapsId
    private ContactInformation contactInformation;

    /**
     * Why use Set instead of List:
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     *
     * Why use OrderBy: https://www.youtube.com/watch?v=p_ngp05KD-8&list=PL50BZOuKafAbXxVJiD9csunZfQOJ5X7hP
     * https://thorben-janssen.com/ordering-vs-sorting-hibernate-use/
     */
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "supplier_product_category",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id")
    )
    @OrderBy(value="productName ASC")
    private SortedSet<ProductCategory> productCategorySet;
}
