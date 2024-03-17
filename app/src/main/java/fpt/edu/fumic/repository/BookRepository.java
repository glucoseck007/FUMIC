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
import fpt.edu.fumic.database.dao.CategoryDAO;
import fpt.edu.fumic.database.dao.ChapterDAO;
import fpt.edu.fumic.database.dao.FavouriteDao;
import fpt.edu.fumic.database.dao.OwnDAO;
import fpt.edu.fumic.database.entity.AuthorEntity;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.CategoryEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.entity.OwnEntity;

public class BookRepository {

    private final BookDAO bookDAO;
    private final CategoryDAO categoryDAO;
    private final OwnDAO ownDAO;
    private final AuthorDAO authorDAO;
    private final ChapterDAO chapterDAO;
    private final FavouriteDao favouriteDao;

    public BookRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        bookDAO = appDatabase.bookDAO();
        ownDAO = appDatabase.ownDAO();
        authorDAO = appDatabase.authorDAO();
        categoryDAO = appDatabase.categoryDAO();
        chapterDAO = appDatabase.chapterDAO();
        favouriteDao = appDatabase.favouriteDao();
    }

    public List<Long> createBook(List<BookEntity> bookEntities) {return bookDAO.insertAllBooks(bookEntities); }

    public long createBook(BookEntity book) {
        return bookDAO.insertBook(book);
    }

    public String getCategoryNameById(int id) {return categoryDAO.getCategoryNameById(id); }

    public LiveData<List<BookEntity>> getAllBooks() {
        return bookDAO.loadAllBooks();
    }

    public int updateOwn(OwnEntity own) {return ownDAO.updateOwn(own); }

    public int updateContent(List<ChapterEntity> list) {return chapterDAO.updateContent(list); }

    public List<BookEntity> getBooks(int status, int limit, int offset) {
        return bookDAO.getBookListAvailable(status, limit, offset);
    }

    public int updateBook(BookEntity book) {
        return bookDAO.updateBook(book);
    }

    public int getLatestBookId() {return bookDAO.getLatestBookId();}

    public Integer getExistBook(String title) {return bookDAO.getExistBook(title); }

    public void deleteBook(int id) { bookDAO.deleteBook(id); }

    public void deleteAuthor(int id) { authorDAO.deleteAuthor(id); }

    public long insertRelationship(int authorId, int bookId) {
        OwnEntity own = new OwnEntity(authorId, bookId);
        return ownDAO.insert(own);
    }

    public void deleteRelationship(int bookId) {ownDAO.deleteRelationship(bookId); }

    public void deleteChapterByBookId(int bookId) {chapterDAO.deleteChapterByBookId(bookId);}
    public void deleteFavouriteBookById(int bookId) {favouriteDao.deleteBookById(bookId); }
    public long insertAuthor(int authorId, String name) {
        AuthorEntity author = new AuthorEntity(authorId, name);
        return authorDAO.insertAuthor(author);
    }
    public Integer getLatestAuthorId() {return authorDAO.getLatestAuthorId(); }

    public int getAuthorIdWhoOwn(int id) {return ownDAO.getAuthorIdWhoOwn(id); }

    public String getAuthorById(int id) {return authorDAO.getAuthorById(id); }

    public Integer getExistAuthor(String name) {return authorDAO.getExistAuthor(name); }

    public int getCategoryId(String name) {return bookDAO.getCategoryId(name); }

    public byte[] getImage(int id) {return bookDAO.getImage(id); }

    public int getRelationship(int bookId, int authorId) {return ownDAO.getRelationship(bookId, authorId);}

    public int insertChapterContent(List<ChapterEntity> chapter) {
        return chapterDAO.insert(chapter).size();
    }
    public void deleteReadByBookId(int bookId) {favouriteDao.deleteBookById(bookId); }
    ////////////////
    public LiveData<List<BookEntity> >getBooksByStatus(int status) {
        return bookDAO.getBooksByStatus(status);
    }
    public void deleteBook(BookEntity book) {
        bookDAO.deleteBook(book);
    }
    public LiveData<List<BookEntity>> getBooksSortedByViews(int categoryId) {
        return bookDAO.getBooksSortedByViews(categoryId);
    }

    public LiveData<CategoryEntity> getCategoryById(int categoryId) {
        // Perform database query to get category by categoryId
        LiveData<CategoryEntity> category = bookDAO.getCategoryById(categoryId);
        return category;
    }
    public LiveData<List<BookEntity>> getBooksByCategoryId(int categoryId) {
        return bookDAO.getBooksByCategoryId(categoryId);
    }
    public LiveData<List<BookEntity>> getBooksSortedByDateDESC(int categoryId) {
        return bookDAO.loadBooksSortedByDateDESC(categoryId);
    }


    public LiveData<List<BookEntity>> getBooksSortedByDateASC(int categoryId) {
        return bookDAO.loadBooksSortedByDateASC(categoryId);
    }


    public LiveData<List<BookEntity>> getBooksSortedByRatingDESC(int categoryId) {
        return bookDAO.loadBooksSortedByRatingDESC(categoryId);
    }

    public List<BookEntity> getBookListAvailable(int status, int limit, int offset) {
        return bookDAO.getBookListAvailable(status, limit, offset);
    }

    public List<BookEntity> searchByTitle(String key) {
        return bookDAO.searchByTitle(key);
    }

    public BookEntity getBookByTitle(String title) {return bookDAO.getBookByTitle(title); }
}
