package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    // создание тестовых данных из XML файла
    public static java.util.List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();

        // Чтение тестовых данных из XML файла
        var mapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper();
        var value = mapper.readValue(new java.io.File("contacts.xml"),
                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<ContactData>>() {});
        result.addAll(value);

        return result;
    }

    @Test
    public void canRemoveContact() throws IOException {
        if (app.contacts().getCount() == 0) {
            // если контактов нет, создаем один из XML данных
            var contacts = contactProvider();
            app.contacts().createContact(contacts.get(0));
        }

        // получаем список контактов до удаления
        var oldContacts = app.contacts().getList();

        // выбираем случайный контакт для удаления
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var contactToRemove = oldContacts.get(index);

        // удаляем контакт
        app.contacts().removeContact(contactToRemove);

        // НЕ вызываем openContactsPage() - метод getList() уже содержит эту логику
        // получаем список контактов после удаления
        var newContacts = app.contacts().getList();

        // формируем ожидаемый список (старый список без удаленного контакта)
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);

        // компаратор для сортировки по ID
        Comparator<ContactData> compareById = Comparator.comparing(ContactData::id);

        // сортируем оба списка для корректного сравнения
        newContacts.sort(compareById);
        expectedList.sort(compareById);

        // сравниваем списки
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    public void canRemoveAllContactsAtOnce() throws IOException {
        if (app.contacts().getCount() == 0) {
            // если контактов нет, создаем один из XML данных
            var contacts = contactProvider();
            app.contacts().createContact(contacts.get(0));
        }

        app.contacts().removeAllContacts();

        // НЕ вызываем openContactsPage() - метод getCount() уже содержит эту логику
        Assertions.assertEquals(0, app.contacts().getCount());
    }
}