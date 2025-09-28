package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }

        // получение списка групп из БД до удаления
        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var groupToRemove = oldGroups.get(index);

        // удаление группы через UI
        app.groups().removeGroup(groupToRemove);

        // получение списка групп из БД после удаления
        var newGroups = app.hbm().getGroupList();

        // формирование ожидаемого списка (старый список без удаленной группы)
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);

        // сравнение списков из БД
        Assertions.assertEquals(newGroups, expectedList);
    }

    @Test
    public void canRemoveAllGroupsAtOnce() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }

        // удаление всех групп через UI
        app.groups().removeAllGroups();

        // проверка количества групп в БД после удаления
        Assertions.assertEquals(0, app.hbm().getGroupCount());
    }
}