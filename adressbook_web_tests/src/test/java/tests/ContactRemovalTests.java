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
        // проверка наличия контактов в БД
        if (app.hbm().getContactCount() == 0) {
            // создаем контакт через UI
            app.contacts().createContact(new ContactData()
                    .withFirstName("First Name")
                    .withLastName("Last Name"));
        }

        // получаем список контактов из БД до удаления
        var oldContactsFromDb = app.hbm().getContactList();

        // получаем список контактов с UI для получения корректных UI ID
        var uiContacts = app.contacts().getList();
        if (uiContacts.isEmpty()) {
            throw new IllegalStateException("No contacts found on UI");
        }

        // выбираем случайный контакт для удаления (используем первый для простоты)
        var contactToRemoveFromUi = uiContacts.get(0);
        var contactIdToRemove = contactToRemoveFromUi.id();

        // находим соответствующий контакт в БД по имени/фамилии
        var contactToRemoveFromDb = oldContactsFromDb.stream()
                .filter(c -> c.firstName().equals(contactToRemoveFromUi.firstName()) &&
                        c.lastName().equals(contactToRemoveFromUi.lastName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Contact not found in DB"));

        // удаляем контакт через UI по UI ID
        app.contacts().removeContact(contactToRemoveFromUi);

        // получаем список контактов из БД после удаления
        var newContactsFromDb = app.hbm().getContactList();

        // формируем ожидаемый список из БД (старый список без удаленного контакта)
        var expectedListFromDb = new ArrayList<>(oldContactsFromDb);
        expectedListFromDb.removeIf(contact -> contact.id().equals(contactToRemoveFromDb.id()));

        // безопасный компаратор для сортировки по ID
        Comparator<ContactData> compareById = (o1, o2) -> {
            if (o1.id().isEmpty() && o2.id().isEmpty()) return 0;
            if (o1.id().isEmpty()) return -1;
            if (o2.id().isEmpty()) return 1;
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };

        // сортируем оба списка из БД для корректного сравнения
        newContactsFromDb.sort(compareById);
        expectedListFromDb.sort(compareById);

        // сравниваем списки из БД
        Assertions.assertEquals(newContactsFromDb, expectedListFromDb);
    }

    @Test
    public void canRemoveAllContactsAtOnce() throws IOException {
        // проверка наличия контактов в БД
        if (app.hbm().getContactCount() == 0) {
            // создаем контакт через UI
            app.contacts().createContact(new ContactData()
                    .withFirstName("First Name")
                    .withLastName("Last Name"));
        }

        // запоминаем количество контактов в БД до удаления
        var oldCount = app.hbm().getContactCount();

        // удаляем все контакты через UI
        app.contacts().removeAllContacts();

        // проверяем количество контактов в БД после удаления
        Assertions.assertEquals(0, app.hbm().getContactCount());

        // дополнительная проверка: количество уменьшилось на ожидаемое значение
        Assertions.assertTrue(oldCount > 0, "Should have contacts to delete");
    }
}