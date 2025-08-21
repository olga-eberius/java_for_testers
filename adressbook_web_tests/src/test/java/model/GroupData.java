package model;

public record GroupData(String name, String header, String footer) {

    //создание объекта с пустыми строками в полях
    public GroupData(){
        this("","","");
    }

    //создание объекта с измененным именем
    public GroupData withName(String name) {
        return new GroupData(name, this.header, this.footer);
    }

    //создание объекта с измененным заголовком
    public GroupData withHeader(String header) {
        return new GroupData(this.name, header, this.footer);
    }

    //создание объекта с измененным подвалом
    public GroupData withFooter(String footer) {
        return new GroupData(this.name, this.header, footer);
    }


}