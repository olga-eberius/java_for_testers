package ru.stqa.mantis.tests;

import org.junit.jupiter.api.BeforeEach;
import ru.stqa.mantis.manager.ApplicationManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() throws IOException {
        // проверяем, что приложение еще не инициализировано
        if (app == null) {
            // загружаем свойства из файла конфигурации
            var properties = new Properties();
            properties.load(new FileReader(System.getProperty("target", "local.properties")));
            // создаем экземпляр менеджера приложения
            app = new ApplicationManager();
            // инициализируем приложение с указанием браузера и свойств
            app.init(System.getProperty("browser", "chrome"), properties);
        }
    }

}