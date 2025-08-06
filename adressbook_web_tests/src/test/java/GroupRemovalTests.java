import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GroupRemovalTests {
    private static WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get("http://localhost/addressbook/");
        driver.manage().window().setSize(new Dimension(1138, 692));
        driver.findElement(By.name("user")).click();
        driver.findElement(By.name("user")).sendKeys("admin");
        driver.findElement(By.name("pass")).sendKeys("secret");
        driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
    }

    @AfterEach
    public void tearDown() {
        driver.findElement(By.linkText("Logout")).click();
        driver.quit();
    }


    @Test
    public void tests() {
        if (!isElemementPresent(By.name("new"))) {
            driver.findElement(By.linkText("groups")).click();
        }
        if (!isElemementPresent(By.name("selected[]"))) {
            driver.findElement(By.name("new")).click();
            driver.findElement(By.name("group_name")).click();
            driver.findElement(By.name("group_name")).sendKeys("group name");
            driver.findElement(By.name("group_header")).click();
            driver.findElement(By.name("group_header")).sendKeys("group header");
            driver.findElement(By.name("group_footer")).sendKeys("group footer");
            driver.findElement(By.name("submit")).click();
            driver.findElement(By.linkText("groups")).click();
        }
        driver.findElement(By.linkText("groups")).click();
        driver.findElement(By.name("selected[]")).click();
        driver.findElement(By.name("delete")).click();
        driver.findElement(By.linkText("groups")).click();
    }

    private boolean isElemementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }
}
