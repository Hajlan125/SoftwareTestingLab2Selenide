import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.io.FileUtils;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;


public class MainTest {
    static LoginPage loginPage;
    static ProfilePage profilePage;
    static DiskPage diskPage;

    @Step("Открытие страницы")
    public void openUrl() {
        open("https://passport.yandex.ru/auth");
    }

    @BeforeAll
    public static void setUp() {

        try {
            FileUtils.deleteDirectory(new File("allure-results"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

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
        openUrl();
        loginPage.loginIn();
        profilePage.diskTransition();
        diskPage.downloadFile();
        diskPage.checkFile();
        diskPage.uploadFile();
        sleep(10000);
    }

    @AfterAll
    public static void endTest(){
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
