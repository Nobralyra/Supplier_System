package finalproject.suppliersystem.supplier;

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
public class SupplierProductCategoryCriticalityView
{
    @Id
    private Long supplierId;

    private String supplierName;

    private Long productCategoryId;

    private int availabilityIssues;

    private CategoryLevel corporateSocialResponsibility;

    private int issuesConcerningCooperation;

    private Long volume;
}
