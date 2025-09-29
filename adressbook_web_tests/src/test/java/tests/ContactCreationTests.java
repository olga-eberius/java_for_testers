package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();

        var mapper = new XmlMapper();
        var value = mapper.readValue(new File("contacts.xml"), new TypeReference<List<ContactData>>() {});
        result.addAll(value);

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

    // проверка создания контактов с различными данными через сравнение списков из БД
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContacts(ContactData contact) {
        // получение списка контактов из БД до создания
        var oldContacts = app.hbm().getContactList();
        // создание нового контакта
        app.contacts().createContact(contact);
        // получение списка контактов из БД после создания
        var newContacts = app.hbm().getContactList();

        // компаратор для сортировки контактов по ID с обработкой пустых значений
        Comparator<ContactData> compareById = (o1, o2) -> {
            // Обработка пустых ID - считаем их меньшими чем любые непустые
            if (o1.id().isEmpty() && o2.id().isEmpty()) {
                return 0;
            }
            if (o1.id().isEmpty()) {
                return -1;
            }
            if (o2.id().isEmpty()) {
                return 1;
            }
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };

        newContacts.sort(compareById);

        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);

        // сравнение списков контактов из БД
        Assertions.assertEquals(newContacts, expectedList);
    }

    // проверка невозможности создания контактов с апострофами через сравнение списков из БД
    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContactWithApostrophes(ContactData contact) {
        // получение списка контактов из БД до попытки создания
        var oldContacts = app.hbm().getContactList();
        // попытка создания контакта с невалидными данными
        app.contacts().createContact(contact);
        // получение списка контактов из БД после попытки создания
        var newContacts = app.hbm().getContactList();
        // сравнение списков контактов из БД (контакт не создан)
        Assertions.assertEquals(newContacts, oldContacts);
    }

    // тест со всеми заполненными полями через сравнение списков из БД
    @Test
    public void canCreateContactWithAllFields() {
        // получение списка контактов из БД до создания
        var oldContacts = app.hbm().getContactList();

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
                .withBirthdayDay("15")
                .withBirthdayMonth("December")
                .withBirthdayYear("1988")
                .withAnniversaryDay("10")
                .withAnniversaryMonth("December")
                .withAnniversaryYear("1988");

        // создание нового контакта
        app.contacts().createContact(contact);
        // получение списка контактов из БД после создания
        var newContacts = app.hbm().getContactList();

        // компаратор для сортировки контактов по ID с обработкой пустых значений
        Comparator<ContactData> compareById = (o1, o2) -> {
            // Обработка пустых ID
            if (o1.id().isEmpty() && o2.id().isEmpty()) {
                return 0;
            }
            if (o1.id().isEmpty()) {
                return -1;
            }
            if (o2.id().isEmpty()) {
                return 1;
            }
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };

        newContacts.sort(compareById);

        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);

        // сравнение списков контактов из БД
        Assertions.assertEquals(newContacts, expectedList);
    }

    // тест с пустыми данными через сравнение списков из БД
    @Test
    public void canCreateContactWithEmptyData() {
        // получение списка контактов из БД до создания
        var oldContacts = app.hbm().getContactList();
        // создание контакта с пустыми данными
        var emptyContact = new ContactData();
        app.contacts().createContact(emptyContact);
        // получение списка контактов из БД после создания
        var newContacts = app.hbm().getContactList();
        // компаратор для сортировки контактов по ID с обработкой пустых значений
        Comparator<ContactData> compareById = (o1, o2) -> {
            // Обработка пустых ID
            if (o1.id().isEmpty() && o2.id().isEmpty()) {
                return 0;
            }
            if (o1.id().isEmpty()) {
                return -1;
            }
            if (o2.id().isEmpty()) {
                return 1;
            }
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        // Находим ID созданного контакта (последний в отсортированном списке)
        String newContactId = newContacts.get(newContacts.size() - 1).id();
        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        // Добавляем контакт с тем же ID, что и созданный
        expectedList.add(emptyContact.withId(newContactId));
        expectedList.sort(compareById);
        // сравнение списков контактов из БД
        Assertions.assertEquals(newContacts, expectedList);
    }

    // простой тест только с обязательными полями через сравнение списков из БД
    @Test
    public void canCreateContactWithRequiredFieldsOnly() {
        // получение списка контактов из БД до создания
        var oldContacts = app.hbm().getContactList();

        ContactData contact = new ContactData()
                .withFirstName("Olga")
                .withLastName("Eberius");

        // создание нового контакта
        app.contacts().createContact(contact);
        // получение списка контактов из БД после создания
        var newContacts = app.hbm().getContactList();

        // компаратор для сортировки контактов по ID с обработкой пустых значений
        Comparator<ContactData> compareById = (o1, o2) -> {
            // Обработка пустых ID
            if (o1.id().isEmpty() && o2.id().isEmpty()) {
                return 0;
            }
            if (o1.id().isEmpty()) {
                return -1;
            }
            if (o2.id().isEmpty()) {
                return 1;
            }
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };

        newContacts.sort(compareById);

        // формирование ожидаемого списка контактов
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);

        // сравнение списков контактов из БД
        Assertions.assertEquals(newContacts, expectedList);
    }

    // тест создания контакта с фото через сравнение списков из БД
    @Test
    public void canCreateContactWithPhoto() {
        // получение списка контактов из БД до создания
        var oldContacts = app.hbm().getContactList();

        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));

        // создание контакта
        app.contacts().createContact(contact);

        // получение списка контактов из БД после создания
        var newContacts = app.hbm().getContactList();

        // проверка увеличения количества контактов в БД
        Assertions.assertEquals(oldContacts.size() + 1, newContacts.size());
    }

    // тест добавления контакта в группу через проверку БД
    @Test
    public void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));

        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }
        var group = app.hbm().getGroupList().get(0);

        // получение списка контактов в группе из БД до создания
        var oldRelated = app.hbm().getContactsInGroup(group);

        // создание контакта в группе
        app.contacts().create(contact, group);

        // получение списка контактов в группе из БД после создания
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }

    // тест удаления контакта из группы через проверку БД
    @Test
    void canRemoveContactFromGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }
        var group = app.hbm().getGroupList().get(0);

        // если в группе нет контактов, создаем контакт в этой группе
        if (app.hbm().getContactsInGroup(group).isEmpty()) {
            var contact = new ContactData()
                    .withFirstName(CommonFunctions.randomString(10))
                    .withLastName(CommonFunctions.randomString(10));

            // создание контакта в группе
            app.contacts().create(contact, group);
        }

        // получение списка контактов в группе из БД до удаления
        var oldRelated = app.hbm().getContactsInGroup(group);

        // получаем реальный ID из UI
        var realContactId = app.getFirstContactIdInGroup(group);

        // Создаем контакт с реальным ID
        var contactToRemove = new ContactData()
                .withId(realContactId)
                .withFirstName(oldRelated.get(0).firstName())
                .withLastName(oldRelated.get(0).lastName());

        // удаление контакта из группы
        app.contacts().removeContactFromGroup(contactToRemove, group);

        // получение списка контактов в группе из БД после удаления
        var newRelated = app.hbm().getContactsInGroup(group);

        // проверка, что количество контактов в группе уменьшилось на 1
        Assertions.assertEquals(oldRelated.size() - 1, newRelated.size());
    }


}