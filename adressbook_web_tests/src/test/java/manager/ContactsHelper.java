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

    // метод для открытия страницы контактов с ожиданием
    public void openContactsPage() {
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(10));

        // проверяем, не находимся ли мы уже на странице контактов
        if (!manager.isElementPresent(By.name("searchstring"))) {
            // если не на странице, ждем кликабельности ссылки и кликаем
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("home"))).click();

            // ждем загрузки страницы контактов (появления элемента "searchstring")
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
        }
        // если уже на странице, просто выходим из метода
    }

    // метод для создания контакта с улучшенной логикой
    public void createContact(ContactData contact) {
        openContactsPage();
        // добавлена пауза для загрузки
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(5));

        initContactCreation();

        // ждем появления формы создания контакта
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
        fillContactForm(contact);
        submitContactCreation();
        returnToContactsPage();
    }

    // метод для удаления контакта
    public void removeContact() {
        openContactsPage();
        selectFirstContact();
        deleteSelectedContacts();
        returnToContactsPage();
    }

    // изменение контакта
    public void modifyContact(ContactData modifiedContact) {
        openContactsPage();
        selectFirstContact();
        initContactModification();
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToContactsPage();
    }

    // удаление всех контактов
    public void removeAllContacts() {
        openContactsPage();
        selectAllContacts();
        deleteSelectedContacts();
    }

    // метод для получения количества контактов
    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    // метод для инициализации создания контакта
    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    // метод для инициализации модификации контакта
    private void initContactModification() {
        click(By.xpath("//img[@title='Edit']"));
    }

    // метод для заполнения формы контакта
    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("middlename"), contact.middleName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("nickname"), contact.nickname());
        if (contact.photo() != null && !contact.photo().isEmpty()) {
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

    // создание контакта
    private void submitContactCreation() {
        click(By.name("submit"));
    }

    // метод для подтверждения модификации контакта
    private void submitContactModification() {
        click(By.name("update"));
    }

    // возврат на страницу контактов
    private void returnToContactsPage() {
        if (isElementPresent(By.linkText("home"))) {
            click(By.linkText("home"));
        }
    }

    // выбор первого контакта
    private void selectFirstContact() {
        click(By.name("selected[]"));
    }

    // выбор всех контактов
    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    // удаление выбранных контактов
    private void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
        waitForContactsList();
    }


    //ожидание загрузки списка контактов
    private void waitForContactsList() {
        new WebDriverWait(manager.driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
    }

    //проверка наличия контактов
    public boolean isContactPresent() {
        openContactsPage();
        return isElementPresent(By.name("selected[]"));
    }
}