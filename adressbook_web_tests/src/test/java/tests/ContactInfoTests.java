package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {
    private ContactData testContact;

    @Test
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
    }

}