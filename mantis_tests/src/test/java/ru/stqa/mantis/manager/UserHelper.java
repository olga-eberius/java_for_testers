package ru.stqa.mantis.manager;

import org.openqa.selenium.By;
import ru.stqa.mantis.tests.TestBase;

import java.time.Duration;

public class UserHelper extends HelperBase {
    public UserHelper(ApplicationManager manager) {
        super(manager);
    }

    public static void finishCreation(String username, String password) {
        // Заполняем форму завершения регистрации
        TestBase.app.driver().findElement(org.openqa.selenium.By.id("realname")).sendKeys(username);
        TestBase.app.driver().findElement(org.openqa.selenium.By.id("password")).sendKeys(password);
        TestBase.app.driver().findElement(org.openqa.selenium.By.id("password-confirm")).sendKeys(password);
        TestBase.app.driver().findElement(org.openqa.selenium.By.cssSelector("button[type=\"submit\"].btn-success")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startCreation(String username, String email) {
        TestBase.app.driver().get(TestBase.app.property("web.baseUrl") + "/signup_page.php");
        TestBase.app.driver().findElement(org.openqa.selenium.By.name("username")).sendKeys(username);
        TestBase.app.driver().findElement(org.openqa.selenium.By.name("email")).sendKeys(email);
        TestBase.app.driver().findElement(org.openqa.selenium.By.cssSelector("input[type='submit']")).click();
    }

    public void finishRegistration(String password) {
        manager.driver().findElement(By.id("password")).sendKeys(password);
        manager.driver().findElement(By.id("password-confirm")).sendKeys(password);
        manager.driver().findElement(By.cssSelector("button[type=\"submit\"].btn-success")).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}