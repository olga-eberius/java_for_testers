package model;

public record GroupData(String id, String name, String header, String footer) {

    //создание объекта с пустыми строками в полях
    public GroupData(){
        this("", "","","");
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


}