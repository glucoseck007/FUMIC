package fpt.edu.fumic.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "READ", primaryKeys = {"userId", "bookId"},
        foreignKeys = {
                @ForeignKey(entity = BookEntity.class, parentColumns = "id", childColumns = "bookId"),
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "userId")
        })
public class ReadEntity {

    @NonNull
    private String userId;
    @NonNull
    private int bookId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
