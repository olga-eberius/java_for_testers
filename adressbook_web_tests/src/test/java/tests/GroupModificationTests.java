package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;

public class GroupModificationTests extends TestBase{

    @Test
    void canModifyGroup() {
        // если групп нет - создать
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }

        // получаем список групп из БД до модификации
        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var groupToModify = oldGroups.get(index);

        // создаем тестовые данные с новым именем группы
        GroupData testData = new GroupData().withName(CommonFunctions.randomString(10));

        // модифицируем выбранную группу через UI
        app.groups().modifyGroup(groupToModify, testData);

        // получаем список групп из БД после модификации
        var newGroups = app.hbm().getGroupList();

        // создаем ожидаемый список групп
        var expectedList = new ArrayList<>(oldGroups);

        // заменяем модифицированную группу в ожидаемом списке
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));

        // проверяем, что списки из БД совпадают
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    }
}
