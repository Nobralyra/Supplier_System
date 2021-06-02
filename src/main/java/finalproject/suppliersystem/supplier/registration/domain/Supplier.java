package finalproject.suppliersystem.supplier.registration.domain;

import finalproject.suppliersystem.core.auditing.Audition;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * https://projectlombok.org/features/Data
 *
 * SupplierId is customized to sequence with initial value 10.000.
 * https://thorben-janssen.com/jpa-generate-primary-keys/
 *
 * Validations:
 * https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-annotation-processor
 */
//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier extends Audition
{
    @Id
    @Column(length = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_generator")
    @SequenceGenerator(name="my_generator", sequenceName = "supplier_seq", initialValue = 10_000, allocationSize=1)
    private Long supplierId;

    @Column(length = 45)
    @Size(max = 45, message = "Supplier Name must be between 0 and 45 characters")
    @NotBlank(message = "Please add the supplier name")
    private String supplierName;

    /**
     * In a many to many relationship it is not efficient to use a List, but instead use a Set
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     *
     * Supplier owns the association
     */
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "supplier_product_category",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id")
    )
    @NotEmpty(message = "Please choose at least one category")
    private Set<ProductCategory> productCategorySet = new HashSet<>();

    /**
     * Can't rely on a natural identifier for equality checks, so instead we use the entity identifier.
     * The equality has to be consistent across all entity state transitions.
     *
     * To understand why to use equals and hashCode:
     * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#mapping-model-pojo-equalshashcode
     * https://medium.com/@rajibrath20/the-best-way-to-map-a-onetomany-relationship-with-jpa-and-hibernate-dbbf6dba00d3
     * https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
     * https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
     * https://stackoverflow.com/questions/9560522/hibernate-how-to-properly-organize-relation-a-one-to-many-with-annotations
     *
     * @param object - the object to be compared
     * @return boolean
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (!(object instanceof Supplier))
        {
            return false;
        }

        return supplierId != null && supplierId.equals(((Supplier)object).getSupplierId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
