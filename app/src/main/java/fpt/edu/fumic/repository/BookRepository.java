package fpt.edu.fumic.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public List<BookEntity> searchByTitle(String key) {
        List<BookEntity> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String whereClause = "Title like ?";
        String[] whereArgs = {"%"+key+"%"};
        Cursor rs = null;
        try {
            rs = st.query("BOOK", null, whereClause,
                    whereArgs, null, null, null);
            if (rs != null && rs.moveToFirst()) {
                do {
                    int id = rs.getInt(0);
                    String title = rs.getString(1);
                    int noOfChapter = rs.getInt(2);
                    String description = rs.getString(3);
                    int categoryId = rs.getInt(4);
                    int rating = rs.getInt(5);
                    int noOfView = rs.getInt(6);
                    // Lấy ngày tháng dưới dạng chuỗi
                    String dateUploadString = rs.getString(7);
                    Date dateUpload = null;
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        // Chuyển đổi từ chuỗi sang Date
                        dateUpload = dateFormat.parse(dateUploadString);
                    } catch (ParseException e) {
                        // Xử lý ngoại lệ nếu có lỗi
                        e.printStackTrace();
                    }
                    int status = rs.getInt(8);
                    String userId = rs.getString(9);
                    String cover = rs.getString(10);
                    list.add(new BookEntity(id, title, noOfChapter, description, categoryId, rating, noOfView,
                            dateUpload, status, userId, cover));
                } while (rs.moveToNext());
            }
        } finally {
            // Đảm bảo rằng con trỏ Cursor đã được đóng
            if (rs != null) {
                rs.close();
            }
        }
        return list;
    }
}
