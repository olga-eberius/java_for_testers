package manager;

import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GroupHelper extends HelperBase{

    private ApplicationManager applicationManager;

    public GroupHelper (ApplicationManager manager) {
        super(manager);
    }

    /*//открытие страницы Group
    public void openGroupsPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            manager.driver.findElement(By.linkText("groups")).click();
        }
    }*/
    //вариант временный с ожиданием
    public void openGroupsPage() {
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(10));

        // Проверяем, не находимся ли мы уже на странице групп
        if (!manager.isElementPresent(By.name("new"))) {
            // Если не на странице, ждем кликабельности ссылки и кликаем
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("groups"))).click();

            // Ждем загрузки страницы групп (появления элемента "new")
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("new")));
        }
        // Если уже на странице, просто выходим из метода
    }

    //проверка наличия группы
    public boolean isGroupPresent() {
        openGroupsPage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    //метод для создания новой группы
    public void createGroup(GroupData group) {
        openGroupsPage();
        // добавлена пауза для загрузки
        WebDriverWait wait = new WebDriverWait(manager.driver, Duration.ofSeconds(5));

        initGroupCreation();

        // Ждем появления формы создания группы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("group_name")));
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupsPage();
    }

    //метод для удаления группы
    public void removeGroup() {
        openGroupsPage();
        selectGroup();
        removeSelectedGroup();
        returnToGroupsPage();
    }

    public void modifyGroup(GroupData modifiedGroup) {
        openGroupsPage();
        selectGroup();
        initGroupModification();
        fillGroupForm(modifiedGroup);
        submitGroupModification();
        returnToGroupsPage();
    }

    private void submitGroupCreation() {
        click(By.name("submit"));
    }

    private void initGroupCreation() {
        click(By.name("new"));
    }

    private void removeSelectedGroup() {
        click(By.name("delete"));
    }


    private void initGroupModification() {
        click(By.name("edit"));
    }

    private void returnToGroupsPage() {
        click(By.linkText("group page"));
    }

    private void submitGroupModification() {
        click(By.name("update"));
    }

    private void fillGroupForm(GroupData group) {
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
    }

    private void selectGroup() {
        click(By.name("selected[]"));
    }

}
