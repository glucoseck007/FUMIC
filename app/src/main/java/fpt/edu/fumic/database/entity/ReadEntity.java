package fpt.edu.fumic.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "READ", primaryKeys = {"userId", "bookId"},
        foreignKeys = {
                @ForeignKey(entity = BookEntity.class, parentColumns = "id", childColumns = "bookId"),
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "userId")
        })
public class ReadEntity {

    private String userId;
    private int bookId;

}
