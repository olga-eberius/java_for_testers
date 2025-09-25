package manager;

import model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactsHelper extends HelperBase {

    public ContactsHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("middlename"), contact.middleName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("nickname"), contact.nickname());
        if (!contact.photo().isEmpty()) {
            attach(By.name("photo"), contact.photo());
        }
        type(By.name("title"), contact.title());
        type(By.name("company"), contact.company());
        type(By.name("address"), contact.address());
        type(By.name("home"), contact.homePhone());
        type(By.name("mobile"), contact.mobilePhone());
        type(By.name("work"), contact.workPhone());
        type(By.name("fax"), contact.fax());
        type(By.name("email"), contact.email());
        type(By.name("email2"), contact.email2());
        type(By.name("email3"), contact.email3());
        type(By.name("homepage"), contact.homepage());
        select(By.name("bday"), contact.birthdayDay());
        select(By.name("bmonth"), contact.birthdayMonth());
        type(By.name("byear"), contact.birthdayYear());
        select(By.name("aday"), contact.anniversaryDay());
        select(By.name("amonth"), contact.anniversaryMonth());
        type(By.name("ayear"), contact.anniversaryYear());
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    public void removeContact() {
        openContactsPage();
        selectFirstContact();
        deleteSelectedContacts();
    }

    public boolean isContactPresent() {
        return isElementPresent(By.name("selected[]"));
    }

    private void openContactsPage() {
        if (!isElementPresent(By.name("searchstring"))) {
            click(By.linkText("home"));
        }
    }

    private void selectFirstContact() {
        click(By.name("selected[]"));
    }

    private void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
        waitForContactsList();
    }

    private void waitForContactsList() {
        new WebDriverWait(manager.driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
    }

}