package model;

import java.util.Objects;

public record ContactData(String id,
                          String firstName,
                          String middleName,
                          String lastName,
                          String nickname,
                          String photo,
                          String title,
                          String company,
                          String address,
                          String homePhone,
                          String mobilePhone,
                          String workPhone,
                          String fax,
                          String email,
                          String email2,
                          String email3,
                          String homepage,
                          String birthdayDay,
                          String birthdayMonth,
                          String birthdayYear,
                          String anniversaryDay,
                          String anniversaryMonth,
                          String anniversaryYear) {

    //создание контакта с обновленным id
    public ContactData withId(String id) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с пустыми значениями по умолчанию
    public ContactData() {
        this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "-", "-", "", "-", "-", "");
    }

    //создание контакта с обновленным именем
    public ContactData withFirstName(String firstName) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным отчеством
    public ContactData withMiddleName(String middleName) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленной фамилией
    public ContactData withLastName(String lastName) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным псевдонимом
    public ContactData withNickname(String nickname) {
        return new ContactData("", firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленной фотографией
    public ContactData withPhoto(String photo) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным заголовком
    public ContactData withTitle(String title) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленной компанией
    public ContactData withCompany(String company) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным адресом
    public ContactData withAddress(String address) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным домашним телефоном
    public ContactData withHomePhone(String homePhone) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным мобильным телефоном
    public ContactData withMobilePhone(String mobilePhone) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным рабочим телефоном
    public ContactData withWorkPhone(String workPhone) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным факсом
    public ContactData withFax(String fax) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным первым email
    public ContactData withEmail(String email) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным вторым email
    public ContactData withEmail2(String email2) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным третьим email
    public ContactData withEmail3(String email3) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленной домашней страницей
    public ContactData withHomepage(String homepage) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным днем рождения
    public ContactData withBirthdayDay(String birthdayDay) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным месяцем рождения
    public ContactData withBirthdayMonth(String birthdayMonth) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным годом рождения
    public ContactData withBirthdayYear(String birthdayYear) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным днем годовщины
    public ContactData withAnniversaryDay(String anniversaryDay) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным месяцем годовщины
    public ContactData withAnniversaryMonth(String anniversaryMonth) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //создание контакта с обновленным годом годовщины
    public ContactData withAnniversaryYear(String anniversaryYear) {
        return new ContactData(id, firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    //сравнение контактов по имени и фамилии
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    //генерация хэш-кода на основе имени и фамилии
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}