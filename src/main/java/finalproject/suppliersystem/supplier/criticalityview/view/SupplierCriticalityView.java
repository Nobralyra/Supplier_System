package finalproject.suppliersystem.supplier.criticalityview.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finalproject.suppliersystem.core.enums.CategoryLevel;
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

    /**
     * @Transient make Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private List<String> productCategoryList = new ArrayList<>();

    /**
     * @JsonIgnore gets ignore when making the object to JSON format
     * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
     */
    @JsonIgnore
    private int availabilityIssues;

    /**
     * @JsonIgnore gets ignore when making the object to JSON format
     * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
     */
    @JsonIgnore
    private CategoryLevel corporateSocialResponsibility;

    /**
     * @JsonIgnore gets ignore when making the object to JSON format
     * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
     */
    @JsonIgnore
    private int issuesConcerningCooperation;

    private Long volume;

    /**
     * @Transient make Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private CategoryLevel supplierRiskLevel;

    /**
     * @Transient make Hibernate ignore ignore the field
     * https://www.baeldung.com/jpa-transient-ignore-field
     */
    @Transient
    private CategoryLevel criticality;

    public void setProductCategoryList(List<String> productCategoryList)
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
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", productCategoryList=" + productCategoryList +
                ", availabilityIssues=" + availabilityIssues +
                ", corporateSocialResponsibility=" + corporateSocialResponsibility +
                ", issuesConcerningCooperation=" + issuesConcerningCooperation +
                ", volume=" + volume +
                ", supplierRiskLevel=" + supplierRiskLevel +
                ", criticality=" + criticality +
                '}';
    }
}
