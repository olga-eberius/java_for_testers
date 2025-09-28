package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        // проверка наличия контактов для модификации
        if (app.hbm().getContactCount() == 0) {
            // создаем контакт
            app.contacts().createContact(new ContactData()
                    .withFirstName("First Name")
                    .withLastName("Last Name"));
        }

        // получаем контакты со страницы
        var uiContacts = app.contacts().getList();
        if (uiContacts.isEmpty()) {
            throw new IllegalStateException("No contacts found on the web page");
        }

        var contactToModify = uiContacts.get(0);
        var contactId = contactToModify.id();

        System.out.println("Modifying contact with UI ID: " + contactId);

        // создаем копию контакта с измененными только основными полями
        ContactData testData = contactToModify
                .withFirstName("modified first name")
                .withLastName("modified last name");

        // модификация контакта через UI
        app.contacts().modifyContact(contactToModify, testData);

        // получаем актуальный список с веб-страницы после модификации
        var newUiContacts = app.contacts().getList();
        var expectedList = new ArrayList<>(uiContacts);

        // замена модифицированного контакта в ожидаемом списке
        for (int i = 0; i < expectedList.size(); i++) {
            if (expectedList.get(i).id().equals(contactId)) {
                expectedList.set(i, testData.withId(contactId));
                break;
            }
        }

        //компаратор для сортировки контактов по ID
        Comparator<ContactData> compareById = (o1, o2) -> {
            if (o1.id().isEmpty() && o2.id().isEmpty()) return 0;
            if (o1.id().isEmpty()) return -1;
            if (o2.id().isEmpty()) return 1;
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };

        newUiContacts.sort(compareById);
        expectedList.sort(compareById);

        Assertions.assertEquals(newUiContacts, expectedList);
    }
}