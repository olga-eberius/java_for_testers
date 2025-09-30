package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.regex.Pattern;

public class MailTests extends TestBase {
    @Test
    void canDrainInbox() {
        app.mail().drain("user1@localhost", "password");

    }

    @Test
    void canReceiveMail() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        System.out.println(messages);
        Assertions.assertEquals(1, messages.size());
    }

    @Test
    void canExtractUrl() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            var url = text.substring(matcher.start(), matcher.end());
            System.out.println("Получен URL: "+ url);
        }

    }
}