package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        // проверка наличия контактов для модификации
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First Name")
                    .withLastName("Last Name"));
        }

        var oldContacts = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var contactToModify = oldContacts.get(index);
        var contactId = contactToModify.id();

        ContactData testData = new ContactData()
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