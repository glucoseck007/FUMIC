package fpt.edu.fumic.database.entity;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;



@Entity(tableName = "FAVOURITE", primaryKeys = {"userId", "bookId"},
        foreignKeys = {
                @ForeignKey(entity = UserEntity.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = BookEntity.class,
                        parentColumns = "id",
                        childColumns = "bookId",
                        onDelete = ForeignKey.CASCADE)
        })
public class FavouriteEntity {
    @NonNull
    private String userId;
    @NonNull
    private int bookId;

    public FavouriteEntity(@NonNull String userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

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
