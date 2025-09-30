package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.regex.Pattern;

public class MailTests extends TestBase {
    @Test
    void canDrainInbox() {
        // очищаем почтовый ящик от всех писем
        app.mail().drain("user1@localhost", "password");
    }

    @Test
    void canReceiveMail() {
        // получаем письма из почтового ящика с ожиданием до 60 секунд
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        // выводим полученные письма в консоль
        System.out.println(messages);
        // проверяем, что получено ровно одно письмо
        Assertions.assertEquals(1, messages.size());
    }

    @Test
    void canExtractUrl() {
        // получаем письма из почтового ящика
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        // извлекаем текст из первого письма
        var text = messages.get(0).content();
        // создаем шаблон для поиска URL в тексте
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        // если URL найден, извлекаем и выводим его
        if (matcher.find()) {
            var url = text.substring(matcher.start(), matcher.end());
            System.out.println("Получен URL: " + url);
        }

    }
}