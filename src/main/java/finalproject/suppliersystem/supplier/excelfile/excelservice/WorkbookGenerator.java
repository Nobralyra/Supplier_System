package finalproject.suppliersystem.supplier.excelfile.excelservice;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import finalproject.suppliersystem.supplier.domain.ProductCategory;
import finalproject.suppliersystem.supplier.service.SupplierService;
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
 * https://www.codejava.net/coding/how-to-write-excel-files-in-java-using-apache-poi
 */

@Component
public class WorkbookGenerator {

    private final SupplierService supplierService;
    private final ExcelDataGenerator excelDataGenerator;

    public WorkbookGenerator(SupplierService supplierService, ExcelDataGenerator excelDataGenerator) {
        this.supplierService = supplierService;
        this.excelDataGenerator = excelDataGenerator;
    }

    public void createWorkbook() throws IOException{

        XSSFWorkbook workbook = new XSSFWorkbook();

        List<Map<Long,Long>> dataToExcelFile = excelDataGenerator.generateSupplierAndVolumeToExcel();
        Map<Long,Long> lowSuppliers = dataToExcelFile.get(0);
        Map<Long,Long> mediumSuppliers = dataToExcelFile.get(1);
        Map<Long,Long> highSuppliers = dataToExcelFile.get(2);

        createSheet(workbook,CategoryLevel.LOW,lowSuppliers);
        createSheet(workbook,CategoryLevel.MEDIUM,mediumSuppliers);
        createSheet(workbook,CategoryLevel.HIGH,highSuppliers);

        // var is explicit type
        final var path = "C:\\C DATAMATIKER 2020\\Supplier_System\\src\\main\\resources\\files\\Suppliers_by_Spend.xlsx";
        FileOutputStream fileOut = new FileOutputStream(path);
        workbook.write(fileOut);
        fileOut.close();
    }


    /**
     *
     * Sorting map by values:
     * https://howtodoinjava.com/java/sort/java-sort-map-by-values/
     * @param workbook
     * @param sheetName
     * @param dataToSheet
     */
    public void createSheet(XSSFWorkbook workbook, CategoryLevel sheetName, Map<Long,Long> dataToSheet){

        //a sheet with the name LOW, MEDIUM or HIGH
        XSSFSheet sheet = workbook.createSheet(sheetName.toString());

        createColumnNames(workbook,sheet);

        int rowCount = 1;

        /*
            First we need to order suppliers in descenting order by volume (= by value)
            LinkedHashMap preserves the ordering of elements in which they are inserted
            https://howtodoinjava.com/java/sort/java-sort-map-by-values/
        */

        LinkedHashMap<Long, Long> sortedDateMap = new LinkedHashMap<>();

        dataToSheet
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(element -> sortedDateMap.put(element.getKey(), element.getValue()));

        /*
            we loop supplier names (key) and volumes (value), that are stored in a sortedDataMap,
            and creates rows
        */

        for (Map.Entry<Long, Long> pair : sortedDateMap.entrySet()) {
            createRow(sheet, rowCount, pair.getKey(), pair.getValue());
            rowCount++;
        }

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

        List<Set<ProductCategory>> productCategories = null;

        if(sheetName == CategoryLevel.LOW)
            productCategories = excelDataGenerator.generateProductCategories(excelDataGenerator.getLowSuppliers());
        if(sheetName == CategoryLevel.MEDIUM)
            productCategories = excelDataGenerator.generateProductCategories(excelDataGenerator.getMediumSuppliers());
        if(sheetName == CategoryLevel.HIGH)
            productCategories = excelDataGenerator.generateProductCategories(excelDataGenerator.getHighSuppliers());

        int rowCountCategories = 1;

        assert productCategories != null;
        for (Set<ProductCategory> categorySet: productCategories) {

            Cell productCategoryCell = sheet.getRow(rowCountCategories).createCell(2);
            StringBuilder productCategoryNames = new StringBuilder();
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

        for(int i = 0 ; i < 4; i++){
            sheet.autoSizeColumn(i, true);
        }

    }

    /**
     * This method adds data on one row. It creates to cells after each other
     * SupplierName (cell in column 1) and Volume (cell in column 2)
     * SupplierService gets the Supplier Name by the supplierId
     * @param sheet
     * @param rowNumber
     * @param supplierId
     * @param volume
     */
    private void createRow(XSSFSheet sheet, int rowNumber, Long supplierId, Long volume) {

        if (sheet.getRow(rowNumber) == null) sheet.createRow(rowNumber);
        Cell cellSupplierName = sheet.getRow(rowNumber).createCell(0);
        Cell cellVolume = sheet.getRow(rowNumber).createCell(3);
        cellSupplierName.setCellValue(supplierService.findById(supplierId).getSupplierName());
        cellVolume.setCellValue(volume);

    }

    public void createColumnNames(XSSFWorkbook workbook, XSSFSheet sheet){

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        if (sheet.getRow(0) == null) sheet.createRow(0);
        setColumnName(sheet, cellStyle, font, 0,0, "Name");
        setColumnName(sheet, cellStyle, font, 0,1, "Country");
        setColumnName(sheet, cellStyle, font, 0,2, "Product Categories");
        setColumnName(sheet, cellStyle, font, 0,3, "Volume €");
    }

    private void setColumnName(XSSFSheet sheet,
                               XSSFCellStyle cellStyle,
                               XSSFFont font,
                               int rowNumber,
                               int columnIndex,
                               String columnName){

        font.setBold(true);
        cellStyle.setFont(font);
        Cell volumeCell = sheet.getRow(rowNumber).createCell(columnIndex);
        volumeCell.setCellValue(columnName);
        volumeCell.setCellStyle(cellStyle);
    }


}
