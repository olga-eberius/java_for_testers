package ru.stqa.mantis.tests;

import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.MailMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class UserRegistrationTests extends TestBase {

    // создать пользователя(адрес) на почтовом сервере (JamesHelper)
    // заполняем форму создания и отправляем (браузер) - создать класс помощник
    // получаем почту (MailHelper)
    // извлекаем ссылку из письма
    // проходим по ссылке и завершаем регистрацию пользователя(браузер) - создать класс помощник
    // проверяем, что пользователь может залогиниться(httpSessionHelper)

    @Test
    void canRegisterUser() {

        var username = CommonFunctions.randomString(5);
        var email = String.format("%s@localhost", username);
        var password = "password";

        // создаем адрес
        app.jamesCli().addUser(email, password);

        // заполняем форму создания и отправляем
        app.driver().get(app.property("web.baseUrl") + "/signup_page.php");

        // заполняем форму регистрации
        app.driver().findElement(org.openqa.selenium.By.name("username")).sendKeys(username);
        app.driver().findElement(org.openqa.selenium.By.name("email")).sendKeys(email);
        app.driver().findElement(org.openqa.selenium.By.cssSelector("input[type='submit']")).click();


        //ожидаем почту
        List<MailMessage> messages = app.mail().receive(email, password, Duration.ofSeconds(60));
        Assertions.assertEquals(1, messages.size(), "Должно прийти 1 письмо");

        // получаем ссылку из письма
        String text = messages.get(0).content();
        Pattern pattern = Pattern.compile("http://\\S+");
        var matcher = pattern.matcher(text);
        Assertions.assertTrue(matcher.find(), "В письме должна быть ссылка");

        String url = text.substring(matcher.start(), matcher.end());
        System.out.println("Извлеченная ссылка: " + url);

        // переход по ссылке
        app.driver().get(url);

        //заполнение формы
        app.driver().findElement(org.openqa.selenium.By.id("realname")).sendKeys(username);
        app.driver().findElement(org.openqa.selenium.By.id("password")).sendKeys(password);
        app.driver().findElement(org.openqa.selenium.By.id("password-confirm")).sendKeys(password);
        app.driver().findElement(org.openqa.selenium.By.cssSelector("button[type=\"submit\"].btn-success")).click();

        // проверяем что может залогиниться
        app.http().login(username, "password");
        Assertions.assertTrue(app.http().isLoggedIn(), "Пользователь залогинен");
    }

}