import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DiskPage {
    File file;
    public DiskPage() {
        Configuration.downloadsFolder = "build/downloads";
//        Configuration.proxyEnabled = true;
//        Configuration.fileDownload = PROXY;
    }

    @Step
    public void downloadFile(){
        $(By.xpath("html[1]/body[1]/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/span[1]"))
                .shouldHave(text("lab5_1.xlsx")).click();
        $(By.xpath("//body/div[@id='app']/div[1]/div[1]/div[2]/div[1]/div[2]/button[1]")).click();
        sleep(1000);
    }

    @Step
    public void checkFile(){
        try {
            file = searchFileByDeepness("/Users/adelgaraev/Desktop/ТестПО/Lab2Selenide","lab5_1.xlsx");
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheet("Лист1");
            XSSFRow row = sheet.getRow(0);
            Assert.assertEquals("Название", row.getCell(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File searchFileByDeepness(final String directoryName, final String fileName) {
        File target = null;
        if(directoryName != null && fileName != null) {
            File directory = new File(directoryName);
            if(directory.isDirectory()) {
                File file = new File(directoryName, fileName);
                if(file.isFile()) {
                    target = file;
                }
                else {
                    List<File> subDirectories = getSubDirectories(directory);
                    do {
                        List<File> subSubDirectories = new ArrayList<File>();
                        for(File subDirectory : subDirectories) {
                            File fileInSubDirectory = new File(subDirectory, fileName);
                            if(fileInSubDirectory.isFile()) {
                                return fileInSubDirectory;
                            }
                            subSubDirectories.addAll(getSubDirectories(subDirectory));
                        }
                        subDirectories = subSubDirectories;
                    } while(subDirectories != null && ! subDirectories.isEmpty());
                }
            }
        }


        return target;
    }

    private static List<File> getSubDirectories(final File directory) {
        File[] subDirectories = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File current, final String name) {
                return new File(current, name).isDirectory();
            }
        });
        return Arrays.asList(subDirectories);
    }

}
