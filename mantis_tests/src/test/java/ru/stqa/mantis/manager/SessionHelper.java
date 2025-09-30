package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {
    public SessionHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String user, String password) {
        type(By.name("username"), user);
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), password);
        // нажимаем кнопку отправки формы для завершения входа
        click(By.cssSelector("input[type='submit']"));
    }

    public boolean isLoggedIn() {
        // проверяем наличие элемента, указывающего на авторизованного пользователя
        return isElementsPresent(By.cssSelector("span.user-info"));
    }

    public void logout() {
        // если пользователь авторизован, выполняем выход
        if (isLoggedIn()) {
            click(By.cssSelector("a[href*='logout']"));
        }
    }

    public void ensureLoggedOut() {
        // рахлогин
        if (isLoggedIn()) {
            logout();
        }
    }
}