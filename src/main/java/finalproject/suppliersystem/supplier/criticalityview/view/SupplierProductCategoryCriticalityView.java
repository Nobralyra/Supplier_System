package finalproject.suppliersystem.supplier.criticalityview.view;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;

@Entity
@Immutable
@Subselect("select * from supplier_product_category_criticality_view")
@Getter
@Synchronize( {"supplier", "criticality"} )
public class SupplierProductCategoryCriticalityView
{
    @Id
    private Long supplierId;

    private String supplierName;

    private int availabilityIssues;

    private CategoryLevel corporateSocialResponsibility;

    private int issuesConcerningCooperation;

    private Long volume;

    /**
     * @Transient so Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private CategoryLevel supplierRiskLevel;

    /**
     * @Transient so Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private CategoryLevel criticality;

    public void setSupplierRiskLevel(CategoryLevel supplierRiskLevel)
    {
        this.supplierRiskLevel = supplierRiskLevel;
    }

    public void setCriticality(CategoryLevel criticality)
    {
        this.criticality = criticality;
    }
}
