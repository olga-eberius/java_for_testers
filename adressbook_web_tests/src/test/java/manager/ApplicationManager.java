package manager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

//Методы для инициализации драйвера , управления авторизацией и группами
public class ApplicationManager {
    //статические поле для хранение экземпляра Webdriver
    protected WebDriver driver;
    //всполмогательный класс для управления авторизацией
    private LoginHelper session;
    //вспомогательный класс для управления группами
    private GroupHelper groups;
    //вспомогательный класс для управления контактами
    private ContactsHelper contacts;

    private Properties properties;

    public void init(String browser, Properties properties) {
        this.properties = properties;
        if (driver == null) {//если драйвер не инициализирован
            if ("firefox".equals(browser)){
                driver = new FirefoxDriver();  //создаем новый FirefoxDriver
            } else if ("chrome".equals(browser)){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(); //создание драйвера для Chrome
            } else {
                //выброс исключения для неизвестного браузера
                throw new IllegalArgumentException(String.format("Unknow browser %s", browser));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));  //хук для закрытия
            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().setSize(new Dimension(1138, 692));
            driver.findElement(By.name("user")).click();
            session().login(properties.getProperty("web.username"), properties.getProperty("web.password"));
        }
    }

    //получение экземпляра помощника для работы с авторизацией
    public LoginHelper session(){
        if (session == null) {
            session = new LoginHelper(this);
        }
        return session;
    }

    //получение экземпляра помощника для работы с группами
    public GroupHelper groups() {
        if (groups == null) {
            groups = new GroupHelper(this);
        }
        return groups;
    }

    //получение экземпляра помощника для работы с контактами
    public ContactsHelper contacts() {
        if (contacts == null) {
            contacts = new ContactsHelper(this);
        }
        return contacts;
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