package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {
    private ContactData testContact;

    /*@BeforeEach
    void ensurePreconditions() {
        // Проверяем, есть ли контакты в приложении
        if (app.hbm().getContactList().isEmpty()) {
            // Создаем тестовый контакт, если контактов нет
            var contact = new ContactData()
                    .withFirstName("Test")
                    .withLastName("User")
                    .withAddress("123 Main St, City, Country")
                    .withHomePhone("+1234567890")
                    .withMobilePhone("+0987654321")
                    .withWorkPhone("+5555555555")
                    .withEmail1("test@example.com")
                    .withEmail2("test2@example.com")
                    .withEmail3("test3@example.com");

            app.contacts().createContact(contact);
            app.contacts().openHomePage(); // Возвращаемся на главную страницу
        }

        // Выбираем первый контакт для тестирования
        testContact = app.hbm().getContactList().get(0);
    }*/

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

   /* @Test
    void testAddress() {
        // Получаем адрес с главной страницы
        var addressFromHomePage = app.contacts().getAddress(testContact);

        // Получаем полные данные контакта из БД
        var fullContact = app.hbm().getContactById(testContact.id());

        // Сравниваем адрес
        Assertions.assertEquals(fullContact.address(), addressFromHomePage,
                "Адрес на главной странице не совпадает с адресом в БД для контакта: " + testContact.firstName() + " " + testContact.lastName());
    }

    @Test
    void testEmails() {
        // Получаем emails с главной страницы
        var emailsFromHomePage = app.contacts().getEmails(testContact);

        // Получаем полные данные контакта из БД
        var fullContact = app.hbm().getContactById(testContact.id());

        // Формируем ожидаемую строку emails
        var expectedEmails = Stream.of(fullContact.email(), fullContact.email2(), fullContact.email3())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining("\n"));

        // Сравниваем emails
        Assertions.assertEquals(expectedEmails, emailsFromHomePage,
                "Emails на главной странице не совпадают с emails в БД для контакта: " + testContact.firstName() + " " + testContact.lastName());
    }

    @Test
    void testAllContactInfoForSingleContact() {
        // Получаем полные данные контакта из БД
        var fullContact = app.hbm().getContactById(testContact.id());

        // Получаем данные с главной страницы
        var addressFromHomePage = app.contacts().getAddress(testContact);
        var emailsFromHomePage = app.contacts().getEmails(testContact);
        var phonesFromHomePage = app.contacts().getPhones(testContact);

        // Формируем ожидаемые значения
        var expectedEmails = Stream.of(fullContact.email(), fullContact.email2(), fullContact.email3())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining("\n"));

        var expectedPhones = Stream.of(fullContact.homePhone(), fullContact.mobilePhone(), fullContact.workPhone())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining("\n"));

        // Проверяем все поля
        Assertions.assertAll(
                () -> Assertions.assertEquals(fullContact.address(), addressFromHomePage,
                        "Адрес не совпадает для контакта: " + testContact.firstName() + " " + testContact.lastName()),
                () -> Assertions.assertEquals(expectedEmails, emailsFromHomePage,
                        "Emails не совпадают для контакта: " + testContact.firstName() + " " + testContact.lastName()),
                () -> Assertions.assertEquals(expectedPhones, phonesFromHomePage,
                        "Телефоны не совпадают для контакта: " + testContact.firstName() + " " + testContact.lastName())
        );
    }

    @Test
    void testContactInfoConsistency() {
        // Альтернативный тест - проверяем, что данные на UI соответствуют данным в БД
        var fullContact = app.hbm().getContactById(testContact.id());

        var uiAddress = app.contacts().getAddress(testContact);
        var uiEmails = app.contacts().getEmails(testContact);
        var uiPhones = app.contacts().getPhones(testContact);

        // Проверяем консистентность данных
        Assertions.assertAll(
                () -> Assertions.assertEquals(fullContact.address(), uiAddress, "Адрес не консистентен"),
                () -> Assertions.assertTrue(uiEmails.contains(fullContact.email() != null ? fullContact.email() : ""), "Основной email не найден"),
                () -> Assertions.assertTrue(uiPhones.contains(fullContact.homePhone() != null ? fullContact.homePhone() : ""), "Домашний телефон не найден")
        );
    }*/
}