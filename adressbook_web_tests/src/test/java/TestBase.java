import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

    //статические поле для хранение экземпляра Webdriver
    protected static WebDriver driver;

    //метод для создания новой группы
    protected static void createGroup(String group_name, String group_header, String group_footer) {
        driver.findElement(By.name("new")).click();
        driver.findElement(By.name("group_name")).click();
        driver.findElement(By.name("group_name")).sendKeys(group_name);
        driver.findElement(By.name("group_header")).click();
        driver.findElement(By.name("group_header")).sendKeys(group_header);
        driver.findElement(By.name("group_footer")).sendKeys(group_footer);
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.linkText("groups")).click();
    }

    //метод для удаления группы
    protected static void removeGroup() {
        driver.findElement(By.name("selected[]")).click();
        driver.findElement(By.name("delete")).click();
        driver.findElement(By.linkText("groups")).click();
    }

    //pre-request, выполняется перед каждым тестом
    @BeforeEach
    public void setUp() {
        if (driver == null) {  //если драйвер не инициализирован
            driver = new FirefoxDriver();  //создаем новый FirefoxDriver
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));  //хук для закрытия
            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(1138, 692));
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
        }
    }

    //проверка наличия элемента на странице
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    //открытие страницы Group
    protected void openGroupsPage() {
        if (!isElementPresent(By.name("new"))) {
            driver.findElement(By.linkText("groups")).click();
        }
    }

    //проверка наличия группы
    protected boolean isGroupPresent() {
        return isElementPresent(By.name("selected[]"));
    }
}
