package finalproject.suppliersystem.supplier.excelfile.excelcontroller;

import finalproject.suppliersystem.supplier.excelfile.excelservice.WorkbookGenerator;
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

    /**
     * This GetMapping receives link "excel" from HTML "supplier_reports"
     * It produces zip with ResponseBody,
     * that includes the excel file "Suppliers_by_Spend", the user can download.
     * Method has a @ResponseBody, that includes Seriazible, but it also could return byte[].
     * But Serializable is more generic (sequence af bytes).
     * //https://stackoverflow.com/questions/27952949/spring-rest-create-zip-file-and-send-it-to-the-client/40498539
     * @return zip with the file as a Serializable
     * TODO: zip-filen bliver ikke genereret i FireFox
     * @throws IOException
     */
    @GetMapping(value = "/excel", produces = "application/zip")
    public @ResponseBody Serializable generateExcel() throws IOException {

         /*
            datatype "var":
            "In Java 10, the var keyword allows local variable type inference, which means the type for
            the local variable will be inferred by the compiler, so you don't need to declare that."
            https://developers.redhat.com/blog/2018/05/25/simplify-local-variable-type-definition-using-the-java-10-var-keyword
         */
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
