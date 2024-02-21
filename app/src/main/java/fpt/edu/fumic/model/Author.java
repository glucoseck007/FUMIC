package fpt.edu.fumic.model;

public class Author {

    private int id;
    private String name;
    private int age;
    private String information;

    public Author() {};

    public Author(int id, String name, int age, String information) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
