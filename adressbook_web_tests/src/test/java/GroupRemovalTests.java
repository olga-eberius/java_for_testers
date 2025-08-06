import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase{

    @Test
    public void tests() {
        openGroupsPage();
        if (!isGroupPresent()) {
            createGroup("group name", "group header", "group footer");
        }
        removeGroup();
    }

}
