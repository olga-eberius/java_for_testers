package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Создание групп
public class GroupCreationTests extends TestBase{

    // тестовые данные негативных сценариев
    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }

    public static List<GroupData> singleRandomGroup(){
        return List.of(new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30)));

    }

    // Проверка создания групп с различными валидными данными
    @ParameterizedTest
    @MethodSource("singleRandomGroup")
    public void canCreateGroup(GroupData group) {
        // получение списка групп до создания
        var oldGroups = app.jdbc().getGroupList();
        // создание новой группы
        app.groups().createGroup(group);
        // получение списка групп после создания
        var newGroups = app.jdbc().getGroupList();
        // компаратор для сортировки групп по ID
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        var maxId = newGroups.get(newGroups.size() - 1).id();

        // формирование ожидаемого списка групп
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(maxId));
        expectedList.sort(compareById);
        // сравнение списков групп
        Assertions.assertEquals(newGroups, expectedList);
    }

    // проверка невозможности создания группы с невалидными данными
    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        // получение списка групп до попытки создания
        var oldGroups = app.groups().getList();
        // попытка создания группы с невалидными данными
        app.groups().createGroup(group);
        // получение списка групп после попытки создания
        var newGroups = app.groups().getList();
        // сравнение списков групп (группа не создана)
        Assertions.assertEquals(newGroups, oldGroups);
    }
}

