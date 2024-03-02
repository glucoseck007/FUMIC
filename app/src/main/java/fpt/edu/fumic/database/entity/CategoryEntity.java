package fpt.edu.fumic.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fpt.edu.fumic.database.model.Category;

@Entity(tableName = "CATEGORY")
public class CategoryEntity implements Category {

    @PrimaryKey
    @NonNull
    private int id;
    private String name;

    public CategoryEntity() {};

    public CategoryEntity(int id, String name) {
        this.id = id;
        this.name = name;
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
}
