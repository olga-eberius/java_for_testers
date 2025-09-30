package ru.stqa.mantis.manager;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ApplicationManager {
    private WebDriver driver;
    private String browser;
    private Properties properties;
    private SessionHelper sessionHelper;
    private HttpSessionHelper httpSessionHelper;
    private JamesCliHelper jamesCliHelper;
    private MailHelper mailHelper;

    // инициализация менеджера с указанием браузера и свойств
    public void init(String browser, Properties properties) {
        this.browser = browser;
        this.properties = properties;

    }

    // получение экземпляра веб-драйвера с ленивой инициализацией
    public WebDriver driver() {
        if (driver == null) {
            if ("firefox".equals(browser)) {
                driver = new FirefoxDriver();
            } else if ("chrome".equals(browser)) {
                driver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException(String.format("Unknown browser %s", browser));
            }
            // добавление хука для автоматического закрытия драйвера при завершении работы JVM
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            // открытие базового URL и установка размера окна браузера
            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().setSize(new Dimension(2360, 1321));
        }
        return driver;
    }

    // получение помощника для работы с веб-сессиями
    public SessionHelper session() {
        if (sessionHelper == null) {
            sessionHelper = new SessionHelper(this);
        }
        return sessionHelper;
    }

    // получение помощника для работы с HTTP-сессиями
    public HttpSessionHelper http() {
        if (httpSessionHelper == null) {
            httpSessionHelper = new HttpSessionHelper(this);
        }
        return httpSessionHelper;
    }

    // получение помощника для работы с James CLI
    public JamesCliHelper jamesCli() {
        if (jamesCliHelper == null) {
            jamesCliHelper = new JamesCliHelper(this);
        }
        return jamesCliHelper;
    }

    // получение помощника для работы с почтой
    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    // получение значения свойства по имени
    public String property(String name) {
        return properties.getProperty(name);
    }

}