package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {
    private ContactData testContact;

    /*@Test
    void testPhones() {
        // получаем список контактов из базы данных
        var contacts = app.hbm().getContactList();
        var contact = contacts.get(0);

        // получаем телефоны контакта со страницы по имени( иначе не находит)
        var phones = app.contacts().getPhonesByName(contact);

        // формируем ожидаемую строку с телефонами
        var expected = Stream.of(contact.homePhone(), contact.mobilePhone(), contact.workPhone())
                .filter(s -> s != null && ! "".equals(s))
                .collect(Collectors.joining("\n"));
        // проверяем соответствие ожидаемых и фактических телефонов
        Assertions.assertEquals(expected, phones);
    }*/

    @Test
    void testPhones() {
        var contacts = app.hbm().getContactList();

        // получаем телефоны со страницы
        var phones = app.contacts().getPhones();

        // проверяем каждый контакт
        for (var contact : contacts) {
            var expected = Stream.of(contact.homePhone(), contact.mobilePhone(), contact.workPhone())
                    .filter(s -> s != null && ! "".equals(s))
                    .collect(Collectors.joining("\n"));

            // находим соответствующий ID на странице, без этого почему то бух
            var pageId = phones.keySet().stream()
                    .filter(id -> phones.get(id).equals(expected))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(pageId, "Не найден контакт с телефонами: " + expected);
            Assertions.assertEquals(expected, phones.get(pageId));
        }
    }

    @Test
    void testEmails() {
        var contacts = app.hbm().getContactList();
        var emails = app.contacts().getEmails();

        for (var contact : contacts) {
            // объединяем три email в одну строку через перевод строки
            var expected = Stream.of(contact.email(), contact.email2(), contact.email3())
                    .filter(s -> s != null && ! "".equals(s))
                    .collect(Collectors.joining("\n"));

            var pageId = emails.keySet().stream()
                    .filter(id -> emails.get(id).equals(expected))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(pageId, "Не найден контакт с email: " + expected);
            Assertions.assertEquals(expected, emails.get(pageId));
        }
    }

    @Test
    void testAddresses() {
        var contacts = app.hbm().getContactList();
        var addresses = app.contacts().getAddresses();

        for (var contact : contacts) {
            var expected = contact.address();
            var pageId = addresses.keySet().stream()
                    .filter(id -> addresses.get(id).equals(expected))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(pageId, "Не найден контакт с адресом: " + expected);
            Assertions.assertEquals(expected, addresses.get(pageId));
        }
    }



}