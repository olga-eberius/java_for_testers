package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginHelper extends HelperBase{

    public LoginHelper(ApplicationManager manager) {
        super(manager);
    }

    void login(String user, String password) {

        type(By.name("user"), user);
        type(By.name("pass"), password);
        click(By.xpath("//input[@value='Login']"));

        // добавлено ожидание, без него в хроме тест упадет
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(),'Logout')]")));
    }
}
