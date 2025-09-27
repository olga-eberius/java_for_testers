package manager;

import model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ContactsHelper extends HelperBase {

    public ContactsHelper(ApplicationManager manager) {
        super(manager);
    }

    // метод для открытия страницы контактов с ожиданием
    public void openContactsPage() {
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(10));

        if (!manager.isElementPresent(By.name("searchstring"))) {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("home"))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
        }
    }

    // метод для создания контакта
    public void createContact(ContactData contact) {
        openContactsPage();
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(5));

        initContactCreation();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
        fillContactForm(contact);
        submitContactCreation();
        returnToContactsPage();
    }

    // метод для удаления контакта
    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContact(contact);
        deleteSelectedContacts();
        returnToHomePage();
    }

    // метод для модификации контакта
    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openContactsPage();
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToContactsPage();
    }

    // метод для удаления всех контактов
    public void removeAllContacts() {
        openContactsPage();
        selectAllContacts();
        deleteSelectedContacts();
        returnToHomePage();
    }

    // новый метод для возврата на домашнюю страницу через ссылку "home page"
    private void returnToHomePage() {
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(10));
        try {
            // Ждем появления ссылки "home page" и кликаем по ней
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("home page"))).click();
            // Ждем загрузки страницы контактов
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
        } catch (Exception e) {
            // Если ссылки "home page" нет, пробуем стандартный способ
            returnToContactsPage();
        }
    }

    // метод для получения количества контактов
    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    // метод для получения списка всех контактов
    public List<ContactData> getList() {
        openContactsPage();
        var contacts = new ArrayList<ContactData>();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        var rows = manager.driver.findElements(By.name("entry"));

        for (var row : rows) {
            var cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                try {
                    var checkbox = cells.get(0).findElement(By.name("selected[]"));
                    var id = checkbox.getAttribute("value");
                    var lastName = cells.get(1).getText();
                    var firstName = cells.get(2).getText();

                    if (id != null && !id.isEmpty()) {
                        contacts.add(new ContactData()
                                .withId(id)
                                .withFirstName(firstName)
                                .withLastName(lastName));
                    }
                } catch (Exception e) {
                    // пропускаем строку при ошибке обработки
                }
            }
        }
        return contacts;
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void initContactModification(ContactData contact) {
        var xpath = String.format("//tr[.//input[@value='%s']]//img[@alt='Edit']", contact.id());
        var editButton = manager.driver.findElement(By.xpath(xpath));
        editButton.click();
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("middlename"), contact.middleName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("nickname"), contact.nickname());
        //проверить, что фото не null и не пустое
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

        if (contact.birthdayDay() != null && !contact.birthdayDay().isEmpty() && !contact.birthdayDay().equals("-")) {
            select(By.name("bday"), contact.birthdayDay());
        }
        if (contact.birthdayMonth() != null && !contact.birthdayMonth().isEmpty() && !contact.birthdayMonth().equals("-")) {
            select(By.name("bmonth"), contact.birthdayMonth());
        }
        type(By.name("byear"), contact.birthdayYear());

        if (contact.anniversaryDay() != null && !contact.anniversaryDay().isEmpty() && !contact.anniversaryDay().equals("-")) {
            select(By.name("aday"), contact.anniversaryDay());
        }
        if (contact.anniversaryMonth() != null && !contact.anniversaryMonth().isEmpty() && !contact.anniversaryMonth().equals("-")) {
            select(By.name("amonth"), contact.anniversaryMonth());
        }
        type(By.name("ayear"), contact.anniversaryYear());
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void submitContactModification() {
        click(By.name("update"));
    }

    private void returnToContactsPage() {
        if (isElementPresent(By.linkText("home"))) {
            click(By.linkText("home"));
        }
    }

    private void selectContact(ContactData contact) {
        var xpath = String.format("//tr[.//input[@value='%s']]//input[@name='selected[]']", contact.id());
        var checkbox = manager.driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) manager.driver).executeScript("arguments[0].click();", checkbox);
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    private void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));

        try {
            WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            manager.driver.switchTo().alert().accept();
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.msgbox")),
                    ExpectedConditions.presenceOfElementLocated(By.name("searchstring"))
            ));
        } catch (Exception e) {
            // продолжение выполнения если алерт не появился
        }

        waitForContactsList();
    }

    private void waitForContactsList() {
        new WebDriverWait(manager.driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
    }
}