package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

//Создание группы
public class GroupCreationTests extends TestBase{


    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>();
        for (var name: List.of("","group name")){
            for (var header: List.of("","group header")) {
                for (var footer: List.of("", "group header")) {
                    result.add(new GroupData(name, header, footer));
                }
            }
        }
        for(int i = 0; i < 5; i++) {
            result.add(new GroupData(randomString(i * 10), randomString(i * 10), randomString(i * 10)));
        }
        return result;
    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("group name'", "", "")));
        return result;
    }

  /*  //проверка создания группы со всеми заполненными полями
    @ParameterizedTest
    @ValueSource(strings={"group name", "group name'"})
    public void canCreateGroup(String name) {
        int groupCount = app.groups().getCount();
        app.groups().createGroup(new GroupData(name, "group header", "group footer"));
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }*/


  /* //проверка создания группы с пустым именем
    @Test
    public void canCreateGroupWithEmptyName() {
        app.groups().createGroup(new GroupData());

    }*/

    /*//проверка создания группы только с заполненным именем
    @Test
    public void canCreateGroupWithNameOnly() {
        //создание группы сразу с именем
        app.groups().createGroup(new GroupData().withName("some name"));

    }*/

    //проверка создания групп
    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroups(GroupData group) {
        int groupCount = app.groups().getCount();

        app.groups().createGroup(group);

        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    //проверка создания заданного числа групп
    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        int groupCount = app.groups().getCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount, newGroupCount);
    }


}

