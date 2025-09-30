package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTests extends TestBase {

    @Test
    void canLogin() {
        // выполняем вход в систему с логином и паролем
        app.http().login("administrator", "root");
        // проверяем, что пользователь успешно авторизован
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}
