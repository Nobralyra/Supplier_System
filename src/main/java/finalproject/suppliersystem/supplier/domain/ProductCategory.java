package finalproject.suppliersystem.supplier.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.SortedSet;

@Data
@Entity
public class ProductCategory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;

    private String productName;

    @ManyToMany(mappedBy = "productCategorySet")
    private SortedSet<Supplier> supplierSet;
}
