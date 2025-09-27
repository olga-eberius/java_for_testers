package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.NoSuchElementException;
import java.io.File;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;

public class HelperBase {

    protected final ApplicationManager manager;
    protected final WebDriver driver;

    //конструктор базового помощника
    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
        this.driver = manager.driver;
    }

    //метод для выполнения клика по элементу
    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    //метод для ввода текста в поле с проверкой существующего значения
    protected void type(By locator, String text) {
        click(locator);
        if (text != null) {
            //получение текущего значения поля
            String existingText = driver.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                //очистка поля только если текст отличается
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
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

    //метод для прикрепления файла к элементу
    protected void attach(By locator, String file) {
        driver.findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
    }

    //метод для выбора значения из выпадающего списка
    protected void select(By locator, String value) {
        if (value != null) {
            new Select(driver.findElement(locator)).selectByVisibleText(value);
        }
    }
}