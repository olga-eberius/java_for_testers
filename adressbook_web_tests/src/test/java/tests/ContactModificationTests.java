package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    // создание тестовых данных из XML файла
    public static java.util.List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();

        var mapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper();
        var value = mapper.readValue(new java.io.File("contacts.xml"),
                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<ContactData>>() {});
        result.addAll(value);

        return result;
    }

    @Test
    void canModifyContact() throws IOException {
        // проверка наличия контактов для модификации
        if (app.contacts().getCount() == 0) {
            // если контактов нет, создаем один из XML данных
            var contacts = contactProvider();
            app.contacts().createContact(contacts.get(0));
        }

        var oldContacts = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var contactToModify = oldContacts.get(index);
        var contactId = contactToModify.id();

        // создаем копию контакта с измененными только основными полями
        ContactData testData = contactToModify
                .withFirstName("modified first name")
                .withLastName("modified last name");

        app.contacts().modifyContact(contactToModify, testData);

        var newContacts = app.contacts().getList();
        var expectedList = new ArrayList<>(oldContacts);

        // замена модифицированного контакта в ожидаемом списке
        for (int i = 0; i < expectedList.size(); i++) {
            if (expectedList.get(i).id().equals(contactId)) {
                expectedList.set(i, testData.withId(contactId));
                break;
            }
        }

        // сортировка списков для сравнения
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };

        newContacts.sort(compareById);
        expectedList.sort(compareById);

        Assertions.assertEquals(expectedList, newContacts);
    }
}