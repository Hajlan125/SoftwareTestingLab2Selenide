import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.junit.TextReport;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.xlstest.XLS;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class MainTest {
    LoginPage loginPage;
    ProfilePage profilePage;
    DiskPage diskPage;
    WebDriver webDriver;

    @Rule
    public TextReport report = new TextReport();


    @Before
    public void setUp() {
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.PROXY;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true).includeSelenideSteps(true));
        System.setProperty("webdriver.chrome.driver", "chromedriver");

        loginPage = new LoginPage();
        profilePage = new ProfilePage();
        diskPage = new DiskPage();

        Configuration.startMaximized = true;

    }

    @Test
    public void mainTest() {

        open("https://passport.yandex.ru/auth");
        loginPage.loginIn();
        profilePage.diskTransition();
        diskPage.downloadFile();
        diskPage.uploadFile();
        sleep(10000);
    }

    @After
    public void endTest(){
        diskPage.deleteFile();
        try {
            FileUtils.deleteDirectory(new File("build/downloads"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        close();
    }
}
