package manager.hbm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "addressbook")
public class ContactRecord {

    @Id
    @Column(name = "id")
    public int id;

    @Column(name = "firstname")
    public String firstName;

    @Column(name = "middlename")
    public String middleName;

    @Column(name = "lastname")
    public String lastName;

    @Column(name = "nickname")
    public String nickname;

    @Column(name = "photo")
    public String photo;

    @Column(name = "title")
    public String title;

    @Column(name = "company")
    public String company;

    @Column(name = "address")
    public String address;

    @Column(name = "home")
    public String homePhone;

    @Column(name = "mobile")
    public String mobilePhone;

    @Column(name = "work")
    public String workPhone;

    @Column(name = "fax")
    public String fax;

    @Column(name = "email")
    public String email;

    @Column(name = "email2")
    public String email2;

    @Column(name = "email3")
    public String email3;

    @Column(name = "homepage")
    public String homepage;

    @Column(name = "bday")
    public String birthdayDay;

    @Column(name = "bmonth")
    public String birthdayMonth;

    @Column(name = "byear")
    public String birthdayYear;

    @Column(name = "aday")
    public String anniversaryDay;

    @Column(name = "amonth")
    public String anniversaryMonth;

    @Column(name = "ayear")
    public String anniversaryYear;

    public Date deprecated = new Date();

    public ContactRecord() {
    }

    public ContactRecord(int id, String firstName, String middleName, String lastName,
                         String nickname, String photo, String title, String company,
                         String address, String homePhone, String mobilePhone,
                         String workPhone, String fax, String email, String email2,
                         String email3, String homepage, String birthdayDay,
                         String birthdayMonth, String birthdayYear, String anniversaryDay,
                         String anniversaryMonth, String anniversaryYear) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.photo = photo;
        this.title = title;
        this.company = company;
        this.address = address;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.fax = fax;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.homepage = homepage;
        this.birthdayDay = birthdayDay;
        this.birthdayMonth = birthdayMonth;
        this.birthdayYear = birthdayYear;
        this.anniversaryDay = anniversaryDay;
        this.anniversaryMonth = anniversaryMonth;
        this.anniversaryYear = anniversaryYear;
    }
}