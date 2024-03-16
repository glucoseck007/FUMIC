package fpt.edu.fumic.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import fpt.edu.fumic.database.model.Chapter;

@Entity(tableName = "CHAPTER",
        primaryKeys = {"chapterNo", "bookId"},
        foreignKeys = {@ForeignKey(entity = BookEntity.class, parentColumns = "id", childColumns = "bookId")})
public class ChapterEntity implements Chapter {
    private int chapterNo;
    private int bookId;
    private String content;
    private String chapterTitle;

    public ChapterEntity() {}

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    @Override
    public int getBookId() {
        return this.bookId;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public int getChapterNo() {
        return this.chapterNo;
    }

    @Override
    public String getChapterTitle() {return this.chapterTitle; }
}
