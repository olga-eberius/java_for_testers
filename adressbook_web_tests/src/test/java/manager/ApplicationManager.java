package manager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//Методы для инициализации драйвера , управления авторизацией и группами
public class ApplicationManager {
    //статические поле для хранение экземпляра Webdriver
    protected WebDriver driver;
    //всполмогательный класс для управления авторизацией
    private LoginHelper session;
    //всполмогательный класс для управления группами
    private GroupHelper groups;

    public void init(String browser) {
        if (driver == null) {//если драйвер не инициализирован
            if ("firefox".equals(browser)){
                driver = new FirefoxDriver();  //создаем новый FirefoxDriver
            } else if ("chrome".equals(browser)){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException(String.format("Unknow browser %s", browser));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));  //хук для закрытия
            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(1138, 692));
            driver.findElement(By.name("user")).click();
            session().login("admin", "secret");
        }
    }

    public LoginHelper session(){
        if (session == null) {
            session = new LoginHelper(this);
        }
        return session;
    }

    public GroupHelper groups() {
        if (groups == null) {
            groups = new GroupHelper(this);
        }
        return groups;
    }

    //проверка наличия элемента на странице
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

}
