package finalproject.suppliersystem.supplier.excelfile.excelservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.domain.Criticality;
import finalproject.suppliersystem.supplier.domain.ProductCategory;
import finalproject.suppliersystem.supplier.restapi.restservice.CalculatorCriticalityRestService;
import finalproject.suppliersystem.supplier.service.AddressService;
import finalproject.suppliersystem.supplier.service.CriticalityService;
import finalproject.suppliersystem.supplier.service.SupplierService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * This class generates data for the excel sheet "Suppliers by Spend"
 */
@Setter
@Getter
@Component
public class ExcelDataGenerator {

    private final CriticalityService criticalityService;
    private final CalculatorCriticalityRestService calculatorCriticalityRestService;
    private final AddressService addressService;
    private final SupplierService supplierService;

    //supplierId (Long) is a key and volume (Long) is a value in each TreeMap
    private Map<Long,Long> lowSuppliers = new HashMap<>();
    private Map<Long,Long> mediumSuppliers = new HashMap<>();
    private Map<Long,Long> highSuppliers = new HashMap<>();

    public ExcelDataGenerator(CriticalityService criticalityService, CalculatorCriticalityRestService calculatorCriticalityRestService, AddressService addressService, SupplierService supplierService) {
        this.criticalityService = criticalityService;
        this.calculatorCriticalityRestService = calculatorCriticalityRestService;
        this.addressService = addressService;
        this.supplierService = supplierService;
    }

    /**
     * This method divids al supplier in three categories by their volume
     * and stores them in respective TreeMaps, that are sorted by unique keys
     * key = supplierId (the same as id in Criticality), value = volume (from Criticality)
     * @return array with three maps (lowSuppliers, mediumSuppliers, highSuppliers)
     */
    public List<Map<Long,Long>> generateSupplierAndVolumeToExcel(){

        //Supplier and Criticality has one-to-one relationship and ID in Criticality is supplierId
        List<Criticality> allCriticalities = criticalityService.findAll();

        //comparison is done by converting volue to CategoryLevel
        for(Criticality c : allCriticalities){
            Enum<CategoryLevel> volumeLevel = calculatorCriticalityRestService.calculateVolumeLevel(c.getVolume());
            if(volumeLevel == CategoryLevel.LOW) lowSuppliers.put(c.getSupplierId(), c.getVolume());
            if(volumeLevel == CategoryLevel.MEDIUM) mediumSuppliers.put(c.getSupplierId(), c.getVolume());
            if(volumeLevel == CategoryLevel.HIGH) highSuppliers.put(c.getSupplierId(), c.getVolume());
        }

        ArrayList<Map<Long,Long>> alMaps = new ArrayList<>();
        alMaps.add(lowSuppliers);
        alMaps.add(mediumSuppliers);
        alMaps.add(highSuppliers);

        return alMaps;
    }

    public List<String> generateCountriesToExcel(Map<Long,Long> suppliersGroup){

        List<String> countryNames = new ArrayList<>();

        for(Long supplierId : suppliersGroup.keySet()){
            countryNames.add(addressService.findById(supplierId).getCountry().getCountryName());
        }
        return countryNames;
    }

    public List<Set<ProductCategory>> generateProductCategories(Map<Long,Long> suppliersGroup){

        List<Set<ProductCategory>> productCategories = new ArrayList<>();

        for(Long supplierId : suppliersGroup.keySet()){
            productCategories.add(supplierService.findById(supplierId).getProductCategorySet());
        }

        return productCategories;
    }

}
