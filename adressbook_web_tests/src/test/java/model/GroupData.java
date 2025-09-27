package model;

import java.util.Objects;

public final class GroupData {
    private final String id;
    private final String name;
    private final String header;
    private final String footer;

    public GroupData(String id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }

    //создание объекта с пустыми строками в полях
    public GroupData() {
        this("", "", "", "");
    }

    //создание объекта с измененным id
    public GroupData withId(String id) {
        return new GroupData(id, this.name, this.header, this.footer);
    }


    //создание объекта с измененным именем
    public GroupData withName(String name) {
        return new GroupData(this.id, name, this.header, this.footer);
    }

    //создание объекта с измененным заголовком
    public GroupData withHeader(String header) {
        return new GroupData(this.id, this.name, header, this.footer);
    }

    //создание объекта с измененным подвалом
    public GroupData withFooter(String footer) {
        return new GroupData(this.id, this.name, this.header, footer);
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String header() {
        return header;
    }

    public String footer() {
        return footer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GroupData) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.header, that.header) &&
                Objects.equals(this.footer, that.footer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, header, footer);
    }

    @Override
    public String toString() {
        return "GroupData[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "header=" + header + ", " +
                "footer=" + footer + ']';
    }


}