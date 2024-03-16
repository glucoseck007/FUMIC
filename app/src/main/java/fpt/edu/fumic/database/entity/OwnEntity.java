package fpt.edu.fumic.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "OWN", primaryKeys = {"authorId", "bookId"},
        foreignKeys = {
                @ForeignKey(entity = AuthorEntity.class,
                        parentColumns = "id",
                        childColumns = "authorId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = BookEntity.class,
                        parentColumns = "id",
                        childColumns = "bookId",
                        onDelete = ForeignKey.CASCADE)
        })
public class OwnEntity {
    private int authorId;
    private int bookId;

    public OwnEntity() {
    }

    public OwnEntity(int authorId, int bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

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

