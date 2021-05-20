package finalproject.suppliersystem.supplier.excelFile.controller;

import finalproject.suppliersystem.supplier.excelFile.service.WorkbookGenerator;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class ExcelController {

    private final WorkbookGenerator workbookGenerator;

    public ExcelController(WorkbookGenerator workbookGenerator) {
        this.workbookGenerator = workbookGenerator;
    }

    @GetMapping("/excelfile/supplier_reports")
    public String showDownloadPage(){

        return "/excelfile/supplier_reports";
    }

    @GetMapping(value = "/excel", produces = "application/zip")
    public @ResponseBody Serializable generateExcel() throws IOException {

        // var is explicit type
        final var path = "src\\main\\resources\\files\\Suppliers_by_Spend.xlsx";
        workbookGenerator.createWorkbook();
        File file = new File(path);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        FileInputStream fileInputStream = new FileInputStream(file);
        IOUtils.copy(fileInputStream, zipOutputStream);
        fileInputStream.close();
        zipOutputStream.closeEntry();

        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

    }


}
