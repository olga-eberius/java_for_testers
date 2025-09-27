package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupRemovalTests extends TestBase{

    // создание тестовых данных из XML файла
    public static java.util.List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<GroupData>();

        // Чтение тестовых данных из XML файла
        var mapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper();
        var value = mapper.readValue(new java.io.File("groups.xml"),
                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<GroupData>>() {});
        result.addAll(value);

        return result;
    }

    @Test
    public void canRemoveGroup() throws IOException {
        // если групп нет - создать из XML данных
        if (app.groups().getCount() == 0) {
            var groups = groupProvider();
            app.groups().createGroup(groups.get(0));
        }

        var oldGroups = app.groups().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        //запуск метода удаления группы
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.groups().getList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        Assertions.assertEquals(newGroups, expectedList);
    }

    @Test
    public void canRemoveAllGroupsAtOnce() throws IOException {
        // если групп нет - создать из XML данных
        if (app.groups().getCount() == 0) {
            var groups = groupProvider();
            app.groups().createGroup(groups.get(0));
        }

        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.groups().getCount());
    }
}