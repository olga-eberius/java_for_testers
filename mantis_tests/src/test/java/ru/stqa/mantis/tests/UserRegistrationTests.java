package ru.stqa.mantis.tests;

import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.manager.MailHelper;
import ru.stqa.mantis.manager.UserHelper;
import ru.stqa.mantis.model.MailMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UserRegistrationTests extends TestBase {

    @Test
    void canRegisterUser() {

        var username = CommonFunctions.randomString(5);
        var email = String.format("%s@localhost", username);
        //создать пользователя(адрес) на почтовом сервере (JamesHelper)
        //заполняем форму создания и отправляем (браузер) - создать класс помощник
        //получаем почту (MailHelper)
        //извлекаем ссылку из письма
        //проходим по ссылке и завершаем регистрацию пользователя(браузер) - создать класс помощник
        //проверяем, что пользователь может залогиниться(httpSessionHelper)
        var password = "password";

        // Шаг 1: создать адрес на почтовом сервере
        app.jamesCli().addUser(email, password);

        // Шаг 2: заполнить форму создания и отправить (в браузере)
        app.driver().get(app.property("web.baseUrl") + "/signup_page.php");

        // Заполняем форму регистрации
        app.driver().findElement(org.openqa.selenium.By.name("username")).sendKeys(username);
        app.driver().findElement(org.openqa.selenium.By.name("email")).sendKeys(email);
        app.driver().findElement(org.openqa.selenium.By.cssSelector("input[type='submit']")).click();


        // Шаг 3: ждем и получаем почту
        List<MailMessage> messages = app.mail().receive(email, password, Duration.ofSeconds(60));
        Assertions.assertEquals(1, messages.size(), "Должно прийти 1 письмо");

        String text = messages.get(0).content();
        Pattern pattern = Pattern.compile("http://\\S+");
        var matcher = pattern.matcher(text);
        Assertions.assertTrue(matcher.find(), "В письме должна быть ссылка");

        String url = text.substring(matcher.start(), matcher.end());
        System.out.println("Извлеченная ссылка: " + url);

        app.driver().get(url);

        app.driver().findElement(org.openqa.selenium.By.id("realname")).sendKeys(username);
        app.driver().findElement(org.openqa.selenium.By.id("password")).sendKeys(password);
        app.driver().findElement(org.openqa.selenium.By.id("password-confirm")).sendKeys(password);
        app.driver().findElement(org.openqa.selenium.By.cssSelector("button[type=\"submit\"].btn-success")).click();

        app.http().login(username, "password");
        Assertions.assertTrue(app.http().isLoggedIn(), "Пользователь должен быть залогинен");
    }

    public static Stream<String> randomUser() {
        return Stream.of(CommonFunctions.randomString(8));
    }
}