package finalproject.suppliersystem.supplier.criticalityview.view;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.domain.ProductCategory;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Immutable
@Subselect("select * from supplier_criticality_view")
@Getter
@Synchronize( {"supplier", "criticality"} )
public class SupplierCriticalityView
{
    @Id
    private Long supplierId;

    private String supplierName;

    @Transient
    private List<String> productCategoryList = new ArrayList<>();

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

    public void setProductCategorySet(List<String> productCategoryList)
    {
        this.productCategoryList = productCategoryList;
    }

    public void setSupplierRiskLevel(CategoryLevel supplierRiskLevel)
    {
        this.supplierRiskLevel = supplierRiskLevel;
    }

    public void setCriticality(CategoryLevel criticality)
    {
        this.criticality = criticality;
    }

    @Override
    public String toString()
    {
        return "SupplierCriticalityView{" +
                "productCategoryList=" + productCategoryList +
                '}';
    }
}
