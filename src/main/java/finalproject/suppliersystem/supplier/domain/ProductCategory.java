package finalproject.suppliersystem.supplier.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductCategory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank(message = "Please choose at least one")
    private String productCategoryName;

    /**
     * In a many to many relationship it is not efficient to use a List, but instead use a Set
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     */
    @ManyToMany(mappedBy = "productCategorySet")
    private Set<Supplier> supplierSet = new HashSet<>();

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

        if (!(object instanceof ProductCategory))
        {
            return false;
        }

        return productCategoryId != null && productCategoryId.equals(((ProductCategory)object).getProductCategoryId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }

    @Override
    public String toString()
    {
        return productCategoryName;
    }
}
