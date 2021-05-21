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

    /*
       supplierId (Long) is a key and volume (Long) is a value
       These fields are updated in generateSupplierAndVolumeToExcel()
     */
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
     * This method generates interconnected volumes and supplierId's to the excel file
     * divided in three sheets (LOW, MEDIUM, HIGH).
     *
     * There are also two other columns in every sheet (country and product categories),
     * but they are generated separately. This is because suppliers has to be sorted
     * by the volume, that is not unique and can't be the key, but
     * is the value in each map. It is possible to sort by values with
     * Map.Entry.comparingByValue(Comparator.reverseOrder()) - cf. WorkbookGenerator -
     * and here the value can't not consist of a List with different elements.
     * So the value is only volume.
     *
     * It divides all supplier in three categories by their volume
     * and stores them in respective HashMaps (private fields in this class)
     * key in HashMap is supplierId from Criticality (the same as supplierId in Supplier)
     * value in HashMap = volume from Criticality
     * @return List with these Hashmaps (lowSuppliers, mediumSuppliers, highSuppliers)
     */
    public List<Map<Long,Long>> generateSupplierAndVolumeToExcel(){

        //volume is a field in Criticality that is bound to supplier by supplierId
        List<Criticality> allCriticalities = criticalityService.findAll();

        for(Criticality c : allCriticalities){
            //volume is converted to CategoryLevel
            Enum<CategoryLevel> volumeLevel = calculatorCriticalityRestService.calculateVolumeLevel(c.getVolume());
            //and supplierId and volume is stored to the correct HashMap depending the CategoryLevel
            if(volumeLevel == CategoryLevel.LOW) lowSuppliers.put(c.getSupplierId(), c.getVolume());
            if(volumeLevel == CategoryLevel.MEDIUM) mediumSuppliers.put(c.getSupplierId(), c.getVolume());
            if(volumeLevel == CategoryLevel.HIGH) highSuppliers.put(c.getSupplierId(), c.getVolume());
        }

        //all HashMaps are added to List, that this method returns
        ArrayList<Map<Long,Long>> alMaps = new ArrayList<>();
        alMaps.add(lowSuppliers);
        alMaps.add(mediumSuppliers);
        alMaps.add(highSuppliers);

        return alMaps;
    }

    /**
     * This method generates countries to the excel-file.
     * It loops a map with either lowSuppliers, mediumSuppliers or highSuppliers
     * (parameter is called suppliersGroup) and returns a List with
     * countryNames in the same order as suppliers are in the map.
     * Each countyName is connected to the supplier by supplierId through Address.
     * Address has a country as its field and id in Address is supplierId (same as in Supplier).
     * @param suppliersGroup map with supplierId as a key and volume as a value
     * @return List with countryNames
     */
    public List<String> generateCountriesToExcel(Map<Long,Long> suppliersGroup){

        List<String> countryNames = new ArrayList<>();
        //we loop keys (supplierId) in map and get countryName through Address with supplierId
        for(Long supplierId : suppliersGroup.keySet()){
            countryNames.add(addressService.findById(supplierId).getCountry().getCountryName());
        }
        return countryNames;
    }

    /**
     * This method generates productCategories to the excel-file.
     * It loops a map with either lowSuppliers, mediumSuppliers or highSuppliers
     * (parameter is called suppliersGroup)
     * It returns a List with Set of productCategories for each supplier
     * in the same order as suppliers are in the map.
     * Each Set of ProductCategories is connected to the supplier by supplierId.
     * Supplier has productCategories as its field
     *
     * @param suppliersGroup
     * @return
     */
    public List<Set<ProductCategory>> generateProductCategoriesToExcel(Map<Long,Long> suppliersGroup){

        List<Set<ProductCategory>> productCategories = new ArrayList<>();

        //we loop keys (supplierId) in map and get countryName through Address with supplierId
        for(Long supplierId : suppliersGroup.keySet()){
            productCategories.add(supplierService.findById(supplierId).getProductCategorySet());
        }

        return productCategories;
    }

}
