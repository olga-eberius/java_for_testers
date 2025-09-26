package manager;

import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase{

    private ApplicationManager applicationManager;

    public GroupHelper (ApplicationManager manager) {
        super(manager);
    }


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
    public void removeGroup(GroupData group) {
        openGroupsPage();
        selectGroup(group);
        removeSelectedGroup();
        returnToGroupsPage();
    }

    public void modifyGroup(GroupData group, GroupData modifiedGroup) {
        openGroupsPage();
        selectGroup(group);
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
        if (isElementPresent(By.linkText("group page"))) {        //проверка наличия элемента перед кликом
            click(By.linkText("group page"));
        }
    }

    private void submitGroupModification() {
        click(By.name("update"));
    }

    private void fillGroupForm(GroupData group) {
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
    }

    private void selectGroup(GroupData group) {
        click(By.cssSelector(String.format("input[value='%s']", group.id())));
    }

    public int getCount() {
        openGroupsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void removeAllGroups() {
        openGroupsPage();
        selectAllGroups();
        removeSelectedGroup();
    }

    private void selectAllGroups() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox: checkboxes) {
            checkbox.click();
        }
    }

    public List<GroupData> getList() {
        // открываем страницу со списком групп
        openGroupsPage();
        // создаем пустой список для хранения данных групп
        var groups = new ArrayList<GroupData>();
        // находим все элементы, представляющие группы на странице
        var spans = manager.driver.findElements(By.cssSelector("span.group"));

        // обрабатываем каждую найденную группу
        for (var span : spans) {
            var name = span.getText(); // получаем название группы из текста элемента
            var checkbox = span.findElement(By.name("selected[]")); // находим чекбокс внутри элемента группы
            var id = checkbox.getAttribute("value"); // получаем значение атрибута value, который содержит ID группы

            // проверка что id не пустой - для отладки, тест падает
            if (id != null && !id.isEmpty()) {
                // создаем объект GroupData и добавляем его в список
                groups.add(new GroupData().withId(id).withName(name));
            }
        }
        // возвращаем список всех найденных групп
        return groups;
    }


}
