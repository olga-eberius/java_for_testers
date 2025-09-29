package manager;

import manager.hbm.GroupRecord;
import manager.hbm.ContactRecord;
import model.GroupData;
import model.ContactData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HibernateHelper extends HelperBase {

    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        sessionFactory = new Configuration()
                .addAnnotatedClass(GroupRecord.class)
                .addAnnotatedClass(ContactRecord.class)
                .setProperty(AvailableSettings.URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=convertToNull")
                .setProperty(AvailableSettings.USER, "root")
                .setProperty(AvailableSettings.PASS, "")
                .buildSessionFactory();
    }

    // === методы для работы с группами ===

    // преобразование списка записей из базы в список объектов группы
    static List<GroupData> convertGroupList(List<GroupRecord> records) {
        List<GroupData> result = new ArrayList<>();
        for (var record : records) {
            result.add(convertGroup(record));
        }
        return result;
    }

    // преобразование одной записи группы из базы в объект группы
    private static GroupData convertGroup(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    // преобразование объекта группы в запись для базы данных
    private static GroupRecord convertGroup(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
    }

    // получение списка всех групп из базы данных
    public List<GroupData> getGroupList() {
        return convertGroupList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    // получение количества групп в базе данных
    public long getGroupCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    // создание новой группы в базе данных
    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertGroup(groupData));
            session.getTransaction().commit();
        });
    }

    // === методы для работы с контактами ===

    // преобразование списка записей контактов из базы в список объектов контактов
    static List<ContactData> convertContactList(List<ContactRecord> records) {
        List<ContactData> result = new ArrayList<>();
        for (var record : records) {
            result.add(convertContact(record));
        }
        return result;
    }

    // преобразование одной записи контакта из базы в объект контакта
    private static ContactData convertContact(ContactRecord record) {
        String id = record.id == 0 ? "" : String.valueOf(record.id);
        String firstName = record.firstName == null ? "" : record.firstName;
        String lastName = record.lastName == null ? "" : record.lastName;

        return new ContactData()
                .withId(id)
                .withFirstName(firstName)
                .withMiddleName(record.middleName)
                .withLastName(lastName)
                .withNickname(record.nickname)
                .withPhoto(record.photo)
                .withTitle(record.title)
                .withCompany(record.company)
                .withAddress(record.address)
                .withHomePhone(record.homePhone)
                .withMobilePhone(record.mobilePhone)
                .withWorkPhone(record.workPhone)
                .withFax(record.fax)
                .withEmail(record.email)
                .withEmail2(record.email2)
                .withEmail3(record.email3)
                .withHomepage(record.homepage)
                .withBirthdayDay(record.birthdayDay)
                .withBirthdayMonth(record.birthdayMonth)
                .withBirthdayYear(record.birthdayYear)
                .withAnniversaryDay(record.anniversaryDay)
                .withAnniversaryMonth(record.anniversaryMonth)
                .withAnniversaryYear(record.anniversaryYear);
    }

    // преобразование объекта контакта в запись для базы данных
    private static ContactRecord convertContact(ContactData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(
                Integer.parseInt(id),
                data.firstName(),
                data.middleName(),
                data.lastName(),
                data.nickname(),
                data.photo(),
                data.title(),
                data.company(),
                data.address(),
                data.homePhone(),
                data.mobilePhone(),
                data.workPhone(),
                data.fax(),
                data.email(),
                data.email2(),
                data.email3(),
                data.homepage(),
                data.birthdayDay(),
                data.birthdayMonth(),
                data.birthdayYear(),
                data.anniversaryDay(),
                data.anniversaryMonth(),
                data.anniversaryYear()
        );
    }

    // получение списка всех контактов из базы данных
    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactRecord", ContactRecord.class).list();
        }));
    }

    // получение количества контактов в базе данных
    public long getContactCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    // создание нового контакта в базе данных
    public void createContact(ContactData contactData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertContact(contactData));
            session.getTransaction().commit();
        });
    }

   public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return convertContactList(session.get(GroupRecord.class, group.id()).contacts);
        });
    }

}