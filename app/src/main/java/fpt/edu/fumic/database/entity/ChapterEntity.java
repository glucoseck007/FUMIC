package fpt.edu.fumic.database.entity;

import java.io.Serializable;

public class ChapterEntity implements Serializable {
    private int chapterId;
    private int bookId;
    private String chapterNumber;
    private String chapterContent;

    public ChapterEntity(int chapterId, int bookId, String chapterNumber, String chapterContent) {
        this.chapterId = chapterId;
        this.bookId = bookId;
        this.chapterNumber = chapterNumber;
        this.chapterContent = chapterContent;
    }

    public ChapterEntity(int bookId, String chapterNumber, String chapterContent) {
        this.bookId = bookId;
        this.chapterNumber = chapterNumber;
        this.chapterContent = chapterContent;
    }

    public ChapterEntity() {
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }
}
