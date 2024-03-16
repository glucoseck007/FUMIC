package fpt.edu.fumic.repository;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.AuthorDAO;
import fpt.edu.fumic.database.dao.BookDAO;
import fpt.edu.fumic.database.dao.ChapterDAO;
import fpt.edu.fumic.database.dao.OwnDAO;
import fpt.edu.fumic.database.entity.AuthorEntity;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.entity.OwnEntity;

public class BookRepository {

    private final BookDAO bookDAO;
    private final OwnDAO ownDAO;
    private final AuthorDAO authorDAO;
    private final ChapterDAO chapterDAO;


    public BookRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        bookDAO = appDatabase.bookDAO();
        ownDAO = appDatabase.ownDAO();
        authorDAO = appDatabase.authorDAO();
        chapterDAO = appDatabase.chapterDAO();
    }

    public List<Long> createBook(List<BookEntity> bookEntities) {return bookDAO.insertAllBooks(bookEntities); }

    public long createBook(BookEntity book) {
        return bookDAO.insertBook(book);
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return bookDAO.loadAllBooks();
    }

    public List<BookEntity> getBooks(int status, int limit, int offset) {
        return bookDAO.getBookListAvailable(status, limit, offset);
    }

    public void updateBook(BookEntity book) {
        bookDAO.updateBook(book);
    }

    public int getLatestBookId() {return bookDAO.getLatestBookId();}

    public Integer getExistBook(String title) {return bookDAO.getExistBook(title); }

    public void deleteBook(int id) { bookDAO.deleteBook(id); }

    public void deleteAuthor(int id) { authorDAO.deleteAuthor(id); }

    public long insertRelationship(int authorId, int bookId) {
        OwnEntity own = new OwnEntity(authorId, bookId);
        return ownDAO.insert(own);
    }

    public long insertAuthor(int authorId, String name) {
        AuthorEntity author = new AuthorEntity(authorId, name);
        return authorDAO.insertAuthor(author);
    }
    public Integer getLatestAuthorId() {return authorDAO.getLatestAuthorId(); }

    public Integer getExistAuthor(String name) {return authorDAO.getExistAuthor(name); }

    public int getCategoryId(String name) {return bookDAO.getCategoryId(name); }

    public byte[] getImage(int id) {return bookDAO.getImage(id); }

    public int getRelationship(int bookId, int authorId) {return ownDAO.getRelationship(bookId, authorId);}

    public int insertChapterContent(List<ChapterEntity> chapter) {
        return chapterDAO.insert(chapter).size();
    }

    public LiveData<List<BookEntity>> getBooksSortedByViews() {
        return bookDAO.getBooksSortedByViews();
    }

    /*
Date 6/3/2024
List book
 */
    public LiveData<List<BookEntity>> getBooksSortedByDate() {
        return bookDAO.loadBooksSortedByDate();
    }

    public List<BookEntity> getBookListAvailable(int status, int limit, int offset) {
        return bookDAO.getBookListAvailable(status, limit, offset);
    }

    public LiveData<List<BookEntity>> getBooksSortedById() {
        return bookDAO.loadBooksSortedById();
    }
    public List<BookEntity> searchByTitle(String key) {
        return bookDAO.searchByTitle(key);
    }
}
