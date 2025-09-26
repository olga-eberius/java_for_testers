package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GroupModificationTests extends TestBase{

    @Test
    void canModifyGroup(){
        // проверяем, есть ли группы для модификации
        if (app.groups().getCount() == 0) {
            // если групп нет - создаем новую группу
            app.groups().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }
        // получаем список групп до модификации
        var oldGroups = app.groups().getList();
        // создаем генератор случайных чисел
        var rnd = new Random();
        // выбираем случайную группу для модификации
        var index = rnd.nextInt(oldGroups.size());
        // создаем тестовые данные с новым именем группы
        GroupData testData = new GroupData().withName("modified name");
        // модифицируем выбранную группу
        app.groups().modifyGroup(oldGroups.get(index), testData);
        // получаем список групп после модификации
        var newGroups = app.groups().getList();
        // создаем ожидаемый список групп
        var expectedList = new ArrayList<>(oldGroups);
        // заменяем модифицированную группу в ожидаемом списке
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));
        // создаем компаратор для сортировки по ID
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        // сортируем актуальный и ожидаемый списки
        newGroups.sort(compareById);
        expectedList.sort(compareById);
        // проверяем, что списки совпадают
        Assertions.assertEquals(newGroups, expectedList);

    }

}

