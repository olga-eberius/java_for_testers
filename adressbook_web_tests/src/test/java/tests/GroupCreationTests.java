package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class GroupCreationTests extends TestBase{

    // тестовые данные негативных сценариев
    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }

    public static Stream<GroupData> randomGroups(){
        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30));
        return Stream.generate(randomGroup).limit(1);

    }

    // Проверка создания групп с различными валидными данными
    @ParameterizedTest
    @MethodSource("randomGroups")
    public void canCreateGroup(GroupData group) {
        // получение списка групп из БД до создания
        var oldGroups = app.hbm().getGroupList();
        // создание новой группы
        app.groups().createGroup(group);
        // получение списка групп из БД после создания
        var newGroups = app.hbm().getGroupList();
        var extraGroups = newGroups.stream().filter(g -> ! oldGroups.contains(g)).toList();
        var newId = extraGroups.get(0).id();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(newId));
//        expectedList.sort(compareById);
        // сравнение списков групп из БД
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    }

    // проверка невозможности создания группы с невалидными данными
    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        // получение списка групп из БД до попытки создания
        var oldGroups = app.hbm().getGroupList();
        // попытка создания группы с невалидными данными
        app.groups().createGroup(group);
        // получение списка групп из БД после попытки создания
        var newGroups = app.hbm().getGroupList();
        // сравнение списков групп из БД (группа не создана)
        Assertions.assertEquals(newGroups, oldGroups);
    }
}

