package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("Petya")
                    .withLastName("Vasechkin"));
        }

        // получаем список контактов до удаления
        var oldContacts = app.contacts().getList();

        // выбираем случайный контакт для удаления
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var contactToRemove = oldContacts.get(index);

        // удаляем контакт
        app.contacts().removeContact(contactToRemove);

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
    public void canRemoveAllContactsAtOnce() {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("Petya")
                    .withLastName("Vasechkin"));
        }

        app.contacts().removeAllContacts();
        Assertions.assertEquals(0, app.contacts().getCount());
    }
}