import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    WebDriver webDriver;

    public LoginPage() {

    }

    @Step
    public void loginIn() {
        $(By.id("passp-field-login")).val("AdRGaraev").pressEnter();
        $(By.id("passp-field-passwd")).val("zut47wt8").pressEnter();
        $(By.className("personal-info-login")).shouldHave(text("AdRGaraev"));
    }
}
