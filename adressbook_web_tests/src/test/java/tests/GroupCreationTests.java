package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Создание групп
public class GroupCreationTests extends TestBase{

    // создание тестовых данных для позитивных сценариев
    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>();
        // с пустыми и непустыми значениями
        for (var name: List.of("","group name")){
            for (var header: List.of("","group header")) {
                for (var footer: List.of("", "group header")) {
                    result.add(new GroupData()
                            .withName(name)
                            .withHeader(header)
                            .withFooter(footer));
                }
            }
        }
        // со случайными данными разной длины
        for(int i = 0; i < 5; i++) {
            result.add(new GroupData()
                    .withName(randomString(i * 10))
                    .withHeader(randomString(i * 10))
                    .withFooter(randomString(i * 10)));
        }
        return result;
    }

    // тестовые данные негативных сценариев
    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }

    // Проверка создания групп с различными валидными данными
    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroups(GroupData group) {
        // получение списка групп до создания
        var oldGroups = app.groups().getList();
        int groupCount = app.groups().getCount();
        // создание новой группы
        app.groups().createGroup(group);
        // получение списка групп после создания
        var newGroups = app.groups().getList();
        // компаратор для сортировки групп по ID
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);

        // формирование ожидаемого списка групп
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(newGroups.get(newGroups.size() - 1).id()).withHeader("").withFooter(""));
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

