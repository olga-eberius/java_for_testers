package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

//Создание группы
public class GroupCreationTests extends TestBase{


    //проверка создания группы со всеми заполненными полями
    @Test
    public void canCreateGroup() {
         app.groups().createGroup(new GroupData("group name", "group header", "group footer"));

    }


   //проверка создания группы с пустым именем
    @Test
    public void canCreateGroupWithEmptyName() {
        app.groups().createGroup(new GroupData());

    }

    //проверка создания группы только с заполненным именем
    @Test
    public void canCreateGroupWithNameOnly() {
        //создание группы сразу с именем
        app.groups().createGroup(new GroupData().withName("some name"));

    }

}
