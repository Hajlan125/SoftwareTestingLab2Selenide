import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DiskPage {

    public DiskPage() {
//        Configuration.proxyEnabled = true;
//        Configuration.fileDownload = FileDownloadMode.PROXY;
    }
    public void downloadFile(){
        $(By.xpath("html[1]/body[1]/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/span[1]"))
                .shouldHave(text("lab5_1.xlsx")).click();
        $(By.xpath("//body/div[@id='app']/div[1]/div[1]/div[2]/div[1]/div[2]/button[1]")).click();
    }
}
