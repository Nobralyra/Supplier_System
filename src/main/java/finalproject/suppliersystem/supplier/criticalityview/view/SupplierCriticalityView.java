package finalproject.suppliersystem.supplier.criticalityview.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finalproject.suppliersystem.core.enums.CategoryLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * View of table supplier and criticality
 * @Immutable to make it a read-only, so updates of the entity will be ignored and not exception thrown
 * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#entity-immutability
 * https://docs.jboss.org/hibernate/orm/5.4/javadocs/org/hibernate/annotations/Immutable.html
 *
 * @Subselect map the entity to a custom SQL query and tells Hibernate that there no physical table but an immutable and read-only entity
 * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#annotations-hibernate-subselect
 *
 * @Synchronize used in conjunction with @Subselect and specify the list of database tables used by the @Subselect
 * and prevent @Subselect from getting stale data when executing its SQL query
 * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#annotations-hibernate-synchronize
 */
@Entity
@Immutable
@Subselect("select * from supplier_criticality_view")
@Getter
@Synchronize( {"supplier", "criticality"} )
@NoArgsConstructor
public class SupplierCriticalityView
{
    /**
     * The identifier property
     */
    @Id
    private Long supplierId;

    private String supplierName;

    /**
     * @Transient make Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private List<String> productCategoryList = new ArrayList<>();

    /**
     * @JsonIgnore gets ignore when making the object to JSON format
     * https://www.baeldung.com/jackson-annotations#2-jsonignore
     */
    @JsonIgnore
    private int availabilityIssues;

    /**
     * @JsonIgnore gets ignore when making the object to JSON format
     * https://www.baeldung.com/jackson-annotations#2-jsonignore
     */
    @JsonIgnore
    private CategoryLevel corporateSocialResponsibility;

    /**
     * @JsonIgnore gets ignore when making the object to JSON format
     * https://www.baeldung.com/jackson-annotations#2-jsonignore
     */
    @JsonIgnore
    private int issuesConcerningCooperation;

    private Long volume;

    /**
     * @Transient make Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private CategoryLevel calculatedSupplierRiskLevel;

    /**
     * @Transient make Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private CategoryLevel calculatedCriticality;

    public void setProductCategoryList(List<String> productCategoryList)
    {
        this.productCategoryList = productCategoryList;
    }

    public void setCalculatedSupplierRiskLevel(CategoryLevel calculatedSupplierRiskLevel)
    {
        this.calculatedSupplierRiskLevel = calculatedSupplierRiskLevel;
    }

    public void setCalculatedCriticality(CategoryLevel calculatedCriticality)
    {
        this.calculatedCriticality = calculatedCriticality;
    }

    @Override
    public String toString()
    {
        return "SupplierCriticalityView{" +
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", productCategoryList=" + productCategoryList +
                ", availabilityIssues=" + availabilityIssues +
                ", corporateSocialResponsibility=" + corporateSocialResponsibility +
                ", issuesConcerningCooperation=" + issuesConcerningCooperation +
                ", volume=" + volume +
                ", supplierRiskLevel=" + calculatedSupplierRiskLevel +
                ", criticality=" + calculatedCriticality +
                '}';
    }
}
