import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.SelenideWait;
import io.qameta.allure.Step;
import com.codeborne.xlstest.XLS;
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
import static org.junit.Assert.assertThat;

public class DiskPage {
    File file;
    public DiskPage() {
        Configuration.downloadsFolder = "build/downloads";

    }

    @Step
    public void downloadFile(){
        $(By.xpath("html[1]/body[1]/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/span[1]"))
                .shouldHave(text("lab5_1.xls")).click();
        try {
            File excelFile = $(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/button[1]")).download();
            sleep(15000);
            file = searchFileByDeepness("/Users/adelgaraev/Desktop/ТестПО/Lab2Selenide","lab5_1.xls");

            XLS xls = new XLS(file);

            assertThat(xls, XLS.containsText("Москва"));
            assertThat(xls, XLS.containsText("Название"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sleep(1000);
    }

    public void uploadFile() {
        refresh();
        File imgFile = new File("z-toxic.jpg");
        $(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/span[1]/span[2]/div[1]/input[1]")).uploadFile(imgFile);

    }

    public void deleteFile() {
        refresh();
        $(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[1]/div[3]/div[1]/div[2]"))
                .shouldHave(text("z-toxic.jpg")).contextClick();
        $(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[4]/div[2]")).shouldHave(text("Удалить"))
                .click();
        sleep(5000);
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
