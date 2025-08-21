package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;


public class GroupRemovalTests extends TestBase{

    @Test
    public void tests() {
         //если групп нет - создать
        if (!app.groups().isGroupPresent()) {
            app.groups().createGroup(new GroupData("group name", "group header", "group footer"));
        }

        //запуск метода удаления группы
        app.groups().removeGroup();
    }

}
