package fpt.edu.fumic.database.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(tableName = "FAVOURITE", primaryKeys = {"userId", "bookId"},
        foreignKeys = {
                @ForeignKey(entity = UserEntity.class,
                        parentColumns = "Id",
                        childColumns = "UserId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = BookEntity.class,
                        parentColumns = "Id",
                        childColumns = "BookId",
                        onDelete = ForeignKey.CASCADE)
        })
public class FavouriteEntity {
    private String userId;
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

