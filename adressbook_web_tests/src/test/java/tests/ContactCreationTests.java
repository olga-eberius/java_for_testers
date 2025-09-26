package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();

        // комбинации пустых и заполненных основных полей
        for (var firstName : List.of("", "Olga")) {
            for (var lastName : List.of("", "Eberius")) {
                for (var email : List.of("", "email@test.ru")) {
                    result.add(new ContactData()
                            .withFirstName(firstName)
                            .withLastName(lastName)
                            .withEmail(email));
                }
            }
        }

        // рандомные данные (только основные поля, без выпадающих списков)
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstName(randomString(i * 5))
                    .withLastName(randomString(i * 5))
                    .withEmail(randomString(i * 5) + "@test.ru"));
        }

        return result;
    }

    // негативные данные для сценариев
    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<ContactData>(List.of(
                new ContactData()
                        .withFirstName("Olga'")
                        .withLastName("Last Name"),
                new ContactData()
                        .withFirstName("Olga")
                        .withLastName("Last'Name")
        ));
        return result;
    }

    // проверка создания контактов с различными данными через сравнение списков
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContacts(ContactData contact) {
        // получение списка контактов до создания
        var oldContacts = app.contacts().getList();
        // создание нового контакта
        app.contacts().createContact(contact);
        // получение списка контактов после создания
        var newContacts = app.contacts().getList();
        // компаратор для сортировки контактов по ID
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);
        // сравнение списков контактов
        Assertions.assertEquals(newContacts, expectedList);
    }

    // проверка невозможности создания контактов с апострофами в имени или фамилии через сравнение списков
    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContactWithApostrophes(ContactData contact) {
        // получение списка контактов до попытки создания
        var oldContacts = app.contacts().getList();
        // попытка создания контакта с невалидными данными
        app.contacts().createContact(contact);
        // получение списка контактов после попытки создания
        var newContacts = app.contacts().getList();
        // сравнение списков контактов (контакт не создан)
        Assertions.assertEquals(newContacts, oldContacts);
    }

    // тест со всеми заполненными полями через сравнение списков (с корректными значениями для селектов)
    @Test
    public void canCreateContactWithAllFields() {
        // получение списка контактов до создания
        var oldContacts = app.contacts().getList();

        // используем корректные значения для выпадающих списков
        ContactData contact = new ContactData()
                .withFirstName("Olga")
                .withMiddleName("Olegovna")
                .withLastName("Eberius")
                .withNickname("QA")
                .withTitle("Title")
                .withCompany("Company")
                .withAddress("NSK")
                .withHomePhone("8989898")
                .withMobilePhone("798988988")
                .withWorkPhone("234234234")
                .withFax("Fax")
                .withEmail("email1@test.ru")
                .withEmail2("email2@test.ru")
                .withEmail3("email3@test.ru")
                .withHomepage("www.rntv.ru")
                // корректные значения для дней (1-31)
                .withBirthdayDay("15")
                .withBirthdayMonth("December")
                .withBirthdayYear("1988")
                .withAnniversaryDay("10")
                .withAnniversaryMonth("December")
                .withAnniversaryYear("1988");

        // создание нового контакта
        app.contacts().createContact(contact);
        // получение списка контактов после создания
        var newContacts = app.contacts().getList();
        // компаратор для сортировки контактов по ID
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);
        // сравнение списков контактов
        Assertions.assertEquals(newContacts, expectedList);
    }

    // тест с пустыми данными через сравнение списков
    @Test
    public void canCreateContactWithEmptyData() {
        // получение списка контактов до создания
        var oldContacts = app.contacts().getList();

        // создание контакта с пустыми данными
        app.contacts().createContact(new ContactData());
        // получение списка контактов после создания
        var newContacts = app.contacts().getList();
        // компаратор для сортировки контактов по ID
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(new ContactData().withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);
        // сравнение списков контактов
        Assertions.assertEquals(newContacts, expectedList);
    }

    // простой тест только с обязательными полями (имя и фамилия)
    @Test
    public void canCreateContactWithRequiredFieldsOnly() {
        // получение списка контактов до создания
        var oldContacts = app.contacts().getList();

        ContactData contact = new ContactData()
                .withFirstName("Olga")
                .withLastName("Eberius");

        // создание нового контакта
        app.contacts().createContact(contact);
        // получение списка контактов после создания
        var newContacts = app.contacts().getList();
        // компаратор для сортировки контактов по ID
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);
        // сравнение списков контактов
        Assertions.assertEquals(newContacts, expectedList);
    }
}