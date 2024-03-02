package fpt.edu.fumic.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "OWN", primaryKeys = {"authorId", "bookId"},
        foreignKeys = {
                @ForeignKey(entity = AuthorEntity.class,
                        parentColumns = "authorId",
                        childColumns = "AuthorId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = BookEntity.class,
                        parentColumns = "bookId",
                        childColumns = "BookId",
                        onDelete = ForeignKey.CASCADE)
        })
public class OwnEntity {
    private int authorId;
    private int bookId;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}

