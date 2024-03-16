package fpt.edu.fumic.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fpt.edu.fumic.database.model.Author;

@Entity (tableName = "AUTHOR")
public class AuthorEntity implements Author {

    @PrimaryKey
    @NonNull
    private int id;
    private String name;
    private int age;
    private String information;

    public AuthorEntity() {};

    public AuthorEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorEntity(int id, String name, int age, String information) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.information = information;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getAge() {
        return age;
    }
    @Override
    public String getInformation() {
        return information;
    }
}
