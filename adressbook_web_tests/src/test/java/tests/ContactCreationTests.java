package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();

        // комбинации пустых и заполненных основных полей - добавлены не все, иначе долгий цикл
        for (var firstName : List.of("", "Olga")) {
            for (var lastName : List.of("", "Eberius")) {
                for (var email : List.of("", "email@test.ru")) {
                    result.add(new ContactData()
                            .withFirstName(firstName)
                            .withLastName(lastName)
                            .withEmail1(email));
                }
            }
        }

        // рандомные данные
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstName(randomString(i * 5))
                    .withLastName(randomString(i * 5))
                    .withEmail1(randomString(i * 5) + "@test.ru"));
        }

        return result;
    }


    //негативные данные для сценариев
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

    // проверка создания контактов с различными данными
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContacts(ContactData contact) {
        int contactCount = app.contacts().getCount();
        app.contacts().createContact(contact);
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount + 1, newContactCount);
    }

    // проверка невозможности создания контактов с апострофами в имени или фамилии
    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContactWithApostrophes(ContactData contact) {
        int contactCount = app.contacts().getCount();
        app.contacts().createContact(contact);
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount, newContactCount);
    }

    // тест со всеми заполненными полями
    @Test
    public void canCreateContactWithAllFields() {
        int contactCount = app.contacts().getCount();

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
                .withEmail1("email1@test.ru")
                .withEmail2("email2@test.ru")
                .withEmail3("email3@test.ru")
                .withHomepage("www.rntv.ru")
                .withBirthdayDay("23")
                .withBirthdayMonth("December")
                .withBirthdayYear("1988")
                .withAnniversaryDay("23")
                .withAnniversaryMonth("December")
                .withAnniversaryYear("1988");

        app.contacts().createContact(contact);

        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount + 1, newContactCount);
    }

    //пустые данные
    @Test
    public void canCreateContactWithEmptyData() {
        int contactCount = app.contacts().getCount();

        app.contacts().createContact(new ContactData());

        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount + 1, newContactCount);
    }
}