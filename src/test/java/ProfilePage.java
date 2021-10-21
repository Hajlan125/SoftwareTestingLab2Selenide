import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.*;

public class ProfilePage {

    @Step("Переход на Яндекс диск")
    public void diskTransition(){
        $(By.className("PageHeader-user")).click();
        $(By.xpath("//header/div[2]/div[2]/div[1]/div[1]/div[1]/ul[1]/ul[1]/li[3]/a[1]")).click();
    }
}
