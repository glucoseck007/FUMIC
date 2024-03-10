package fpt.edu.fumic.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.BookDAO;
import fpt.edu.fumic.database.entity.BookEntity;

public class BookRepository {

    private final BookDAO bookDAO;

    public BookRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        bookDAO = appDatabase.bookDAO();
    }


    public void createBook(List<BookEntity> bookEntities) {
        bookDAO.insertAllBooks(bookEntities);
    }

    public void createBook(BookEntity book) {
        bookDAO.insertBook(book);
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return bookDAO.loadAllBooks();
    }

    public List<BookEntity> getBookListAvailable(int status, int limit, int offset) {
        return bookDAO.getBookListAvailable(status, limit, offset);
    }

    public void updateBook(BookEntity book) {
        bookDAO.updateBook(book);
    }

    /*
Date 6/3/2024
List book
 */
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

    /*
    Date 6/3/2024
    List book
     */
    public LiveData<List<BookEntity>> getBooksSortedById() {
        return bookDAO.loadBooksSortedById();
    }
}
