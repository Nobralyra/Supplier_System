package finalproject.suppliersystem.supplier.excelfile.excelservice;

import finalproject.suppliersystem.core.IService;
import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.registration.domain.ProductCategory;
import finalproject.suppliersystem.supplier.registration.domain.Supplier;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * This class generates a excel file.
 * https://www.codejava.net/coding/how-to-write-excel-files-in-java-using-apache-poi
 */
@Component
public class WorkbookGenerator {

    private final IService<Supplier> iSupplierService;
    private final ExcelDataGenerator excelDataGenerator;

    public WorkbookGenerator(IService<Supplier> iSupplierService, ExcelDataGenerator excelDataGenerator) {
        this.iSupplierService = iSupplierService;
        this.excelDataGenerator = excelDataGenerator;
    }

    /**
     * This method creates the workbook (= one excel file) with XSSFWorkbook
     * It stores supplierId's and volumes in a List with three Map<Long,Long>
     * and creates three sheets with this data.
     * It creates FileOutputStream and writes tha data in to the file.
     * @throws IOException due to File
     */
    public void createWorkbook() throws IOException{

        XSSFWorkbook workbook = new XSSFWorkbook();

        List<Map<Long,Long>> dataToExcelFile = excelDataGenerator.generateSupplierAndVolumeToExcel();
        Map<Long,Long> lowSuppliers = dataToExcelFile.get(0);
        Map<Long,Long> mediumSuppliers = dataToExcelFile.get(1);
        Map<Long,Long> highSuppliers = dataToExcelFile.get(2);

        createSheet(workbook,CategoryLevel.LOW,lowSuppliers);
        createSheet(workbook,CategoryLevel.MEDIUM,mediumSuppliers);
        createSheet(workbook,CategoryLevel.HIGH,highSuppliers);

        /*
            datatype "var":
            "In Java 10, the var keyword allows local variable type inference, which means the type for
            the local variable will be inferred by the compiler, so you don't need to declare that."
            https://developers.redhat.com/blog/2018/05/25/simplify-local-variable-type-definition-using-the-java-10-var-keyword
         */

        final var path = "src\\main\\resources\\files\\Suppliers_by_Spend.xlsx";
        FileOutputStream fileOut = new FileOutputStream(path);
        workbook.write(fileOut);
        fileOut.close();
    }


    /**
     * This method creates a sheet with the name LOW, MEDIUM or HIGH with XSSFSheet
     * It sorts suppliers by volume (value in a map).
     * Then we create columns Name and Volume.
     * After that we create columns Country and Product Categories.
     * @param workbook excel file
     * @param sheetName LOW, MEDIUM or HIGH
     * @param dataToSheet lowSuppliers, mediumSuppliers or highSuppliers
     */
    public void createSheet(XSSFWorkbook workbook, CategoryLevel sheetName, Map<Long,Long> dataToSheet){

        //a sheet with the name LOW, MEDIUM or HIGH
        XSSFSheet sheet = workbook.createSheet(sheetName.toString());
        //this method creates column names in a sheet
        createColumnNames(workbook,sheet);

        /*
            Suppliers has to be ordered in descending order by volume (= value in map)
            LinkedHashMap preserves the ordering of elements in which they are inserted.
            We sort entries in a map:
            sorted() from Stream  represents a functional programming concept.This intermediate operation is
            invoked on a Stream instance and after it finish it's processing, it give a Stream instance as output.
            https://www.geeksforgeeks.org/stream-map-java-examples/
            sorted() uses Comparator to compare values.
            https://howtodoinjava.com/java/sort/java-sort-map-by-values/
        */

        LinkedHashMap<Long, Long> sortedDateMap = new LinkedHashMap<>();

        dataToSheet
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                //key = supplierId, value = volume
                .forEachOrdered(element -> sortedDateMap.put(element.getKey(), element.getValue()));

        /*
            We create cells in rows in a sheet.
            First we create cells in column Name and Volume.
            We loop supplierId's(key) and volumes (value), that are stored in a sortedDataMap,
        */

        int rowCountSupplierIdAndVolume = 1;
        for (Map.Entry<Long, Long> pair : sortedDateMap.entrySet()) {
            createCellsToNameAndVolume(sheet, rowCountSupplierIdAndVolume, pair.getKey(), pair.getValue());
            rowCountSupplierIdAndVolume++;
        }

        /*
           Then we create cells in the column "Name"
         */

        List<String> countryNames = null;
        if(sheetName == CategoryLevel.LOW)
            countryNames = excelDataGenerator.generateCountriesToExcel(excelDataGenerator.getLowSuppliers());
        if(sheetName == CategoryLevel.MEDIUM)
            countryNames = excelDataGenerator.generateCountriesToExcel(excelDataGenerator.getMediumSuppliers());
        if(sheetName == CategoryLevel.HIGH)
            countryNames = excelDataGenerator.generateCountriesToExcel(excelDataGenerator.getHighSuppliers());

        int rowCountCountry = 1;

        assert countryNames != null;
        for (String name : countryNames) {
            Cell countryName = sheet.getRow(rowCountCountry).createCell(1);
            countryName.setCellValue(name);
            rowCountCountry++;
        }

        /*
         And we create cells in the column "Product Categories"
         */

        List<Set<ProductCategory>> productCategories = null;

        if(sheetName == CategoryLevel.LOW)
            productCategories = excelDataGenerator.generateProductCategoriesToExcel(excelDataGenerator.getLowSuppliers());
        if(sheetName == CategoryLevel.MEDIUM)
            productCategories = excelDataGenerator.generateProductCategoriesToExcel(excelDataGenerator.getMediumSuppliers());
        if(sheetName == CategoryLevel.HIGH)
            productCategories = excelDataGenerator.generateProductCategoriesToExcel(excelDataGenerator.getHighSuppliers());

        int rowCountCategories = 1;

        assert productCategories != null;
        for (Set<ProductCategory> categorySet: productCategories) {

            Cell productCategoryCell = sheet.getRow(rowCountCategories).createCell(2);
            StringBuilder productCategoryNames = new StringBuilder();
            /*
               We loop a set and add productCategory names in each cell.
               If the set includes multiple names, we separate them by comma
             */
            int lastElement = 0;
            for(ProductCategory p : categorySet){
                productCategoryNames.append(p.getProductCategoryName());
                lastElement++;
                if(lastElement != categorySet.size()){
                    productCategoryNames.append(", ");
                }
            }
            productCategoryCell.setCellValue(String.valueOf(productCategoryNames));
            rowCountCategories++;
        }

        /*
          column size (we have four columns in a sheet: Name, Country, Product Categories and Volume)
          is adjusted the context in the cells
         */
        for(int i = 0 ; i < 4; i++){
            sheet.autoSizeColumn(i, true);
        }

    }

    /**
     * This method adds in the cells in column Name and column Volume
     * on a row after each other.
     * SupplierName (cell in column 0) nd Volume (cell in column 3)
     * SupplierService gets the Supplier Name by the supplierId
     * @param sheet LOW, MEDIUM or HIGH
     * @param rowNumber
     * @param supplierId
     * @param volume
     */
    private void createCellsToNameAndVolume(XSSFSheet sheet, int rowNumber, Long supplierId, Long volume) {

        if (sheet.getRow(rowNumber) == null) sheet.createRow(rowNumber);
        Cell cellSupplierName = sheet.getRow(rowNumber).createCell(0);
        Cell cellVolume = sheet.getRow(rowNumber).createCell(3);
        cellSupplierName.setCellValue(iSupplierService.findById(supplierId).getSupplierName());
        cellVolume.setCellValue(volume);
    }

    /**
     * This method create column names on the first row:
     * Name, Country, Product Categories and Volume
     * with XSSFCellStyle and XSSFFont as bold.
     * @param workbook
     * @param sheet
     */
    public void createColumnNames(XSSFWorkbook workbook, XSSFSheet sheet){

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        if (sheet.getRow(0) == null) sheet.createRow(0);
        createColumnCells(sheet, cellStyle, font, 0, "Name");
        createColumnCells(sheet, cellStyle, font, 1, "Country");
        createColumnCells(sheet, cellStyle, font, 2, "Product Categories");
        createColumnCells(sheet, cellStyle, font, 3, "Volume €");
    }

    /**
     * This method creates cells with the column names on the first row
     * @param sheet LOW, MEDIUM or HIGH
     * @param cellStyle
     * @param font bold
     * @param columnIndex
     * @param columnName
     */
    private void createColumnCells(XSSFSheet sheet,
                                   XSSFCellStyle cellStyle,
                                   XSSFFont font,
                                   int columnIndex,
                                   String columnName){

        font.setBold(true);
        cellStyle.setFont(font);
        Cell volumeCell = sheet.getRow(0).createCell(columnIndex);
        volumeCell.setCellValue(columnName);
        volumeCell.setCellStyle(cellStyle);
    }


}
