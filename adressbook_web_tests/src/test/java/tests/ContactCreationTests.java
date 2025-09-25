package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase {

    //проверка создания контакта со всеми заполненными полями
    @Test
    public void canCreateContactWithAllFields() {
        app.contacts().createContact(new ContactData()
                .withFirstName("Olga")
                .withMiddleName("Olegovna")
                .withLastName("Eberius")
                .withNickname("QA")
                .withPhoto("src/test/resources/cat.jpg")
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
                .withAnniversaryYear("1988"));
    }

    //проверка создания контакта с минимальными данными (имя и фамилия)
    @Test
    public void canCreateContactWithMinData() {
        app.contacts().createContact(new ContactData()
                .withFirstName("Petya")
                .withLastName("Vasechkin"));
    }

    //проверка создания контакта с пустыми данными
    @Test
    public void canCreateContactWithNullData() {
        app.contacts().createContact(new ContactData());
    }

}
