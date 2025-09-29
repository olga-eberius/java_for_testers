package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;


public class ContactsHelper extends HelperBase {

    public ContactsHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openContactsPage() {
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(10));

        if (!manager.isElementPresent(By.name("searchstring"))) {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("home"))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
        }
    }

    public void createContact(ContactData contact) {
        openContactsPage();
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(5));
        initContactCreation();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
        fillContactForm(contact);
        submitContactCreation();
        returnToContactsPage();
    }

    public void create(ContactData contact, GroupData group) {
        openContactsPage();
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(5));
        initContactCreation();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        returnToContactsPage();
    }

    private void selectGroup(GroupData group){
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContact(contact);
        deleteSelectedContacts();
        returnToHomePage();
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openContactsPage();
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToContactsPage();
    }

    public void removeAllContacts() {
        openContactsPage();
        selectAllContacts();
        deleteSelectedContacts();
        returnToHomePage();
    }

    private void returnToHomePage() {
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("home page"))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("searchstring")));
        } catch (Exception e) {
            returnToContactsPage();
        }
    }

    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

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

            // увеличиваем время ожидания и добавляем условия
            wait = new WebDriverWait(manager.driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.name("searchstring")),
                    ExpectedConditions.presenceOfElementLocated(By.id("search-az")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr[name=entry]"))
            ));
        } catch (Exception e) {
            // если алерт не появился, просто продолжаем
        }

        // Добавляем дополнительную паузу для стабилизации, иначе все бух
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void addContactToGroup(ContactData contact, GroupData group) {
        openContactsPage();
        selectContact(contact);
        new Select(manager.driver.findElement(By.name("to_group"))).selectByValue(group.id());
        click(By.name("add"));
        returnToContactsPage();
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        System.out.println("=== removeContactFromGroup ===");
        System.out.println("Contact ID: " + contact.id() + ", Group ID: " + group.id());

        openContactsPage();

        try {
            // Выбираем группу в фильтре
            var groupSelect = new Select(manager.driver.findElement(By.name("group")));
            groupSelect.selectByValue(group.id());
            System.out.println("Group selected in filter: " + group.id());

            // Ждем обновления списка
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Отладочная информация: какие контакты отображаются после фильтрации
            var displayedContacts = manager.driver.findElements(By.cssSelector("input[name='selected[]']"));
            System.out.println("Отображено контактов после фильтрации: " + displayedContacts.size());

            for (var elem : displayedContacts) {
                String contactId = elem.getAttribute("value");
                System.out.println("Отображен контакт с ID: " + contactId);
            }

            // Выбираем контакт
            selectContact(contact);
            System.out.println("Contact selected");

            // Нажимаем кнопку удаления
            var removeButton = manager.driver.findElement(By.name("remove"));
            removeButton.click();
            System.out.println("Remove button clicked");

            // Ждем обновления
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {
            System.out.println("Error in removeContactFromGroup: " + e.getMessage());
            throw e;
        }

        returnToContactsPage();
    }

    /*public String getPhonesByName(ContactData contact) {
        openContactsPage();

        try {
            // поиск по имени и фамилии в таблице
            var xpath = String.format("//tr[td[2][text()='%s'] and td[3][text()='%s']]/td[6]",
                    contact.lastName(), contact.firstName());
            var element = manager.driver.findElement(By.xpath(xpath));
            return element.getText();
        } catch (NoSuchElementException e) {
            System.out.println("телефоны не найдены для  " + contact.firstName() + " " + contact.lastName());
            return "";
        }
    }*/

    public Map<String, String> getPhones() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var phones = row.findElements(By.tagName("td")).get(5).getText();
            result.put(id, phones);
        }
        return result;
    }

    public Map<String, String> getEmails() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("value");
            var emails = row.findElements(By.tagName("td")).get(4).getText(); // 5-я ячейка
            result.put(id, emails);
        }
        return result;
    }

    public Map<String, String> getAddresses() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("value");
            var address = row.findElements(By.tagName("td")).get(3).getText(); // 4-я ячейка
            result.put(id, address);
        }
        return result;
    }



}