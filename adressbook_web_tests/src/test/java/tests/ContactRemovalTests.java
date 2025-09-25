package tests;

import org.junit.jupiter.api.Test;
import model.ContactData;

public class ContactRemovalTests extends TestBase {

    //проверка удаления контакта
    @Test
    public void canRemoveContact() {
        if (!app.contacts().isContactPresent()) { //проверка наличия контакта перед удалением
            app.contacts().createContact(new ContactData()
                    .withFirstName("Petya")
                    .withLastName("Vasechkin"));
        }
        //проверка выполнения удаления контакта
        app.contacts().removeContact();
    }
}
