package fpt.edu.fumic.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fpt.edu.fumic.database.entity.BookEntity;

@Dao
public interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAllBooks(List<BookEntity> bookEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertBook(BookEntity book);

    @Query("SELECT * FROM BOOK")
    LiveData<List<BookEntity>> loadAllBooks();

    @Query("select * from BOOK where status =:status order by dateUpload asc limit :limit offset :offset")
    List<BookEntity> getBookListAvailable(int status,int limit, int offset);
    /*
    Date 6/3/2024
    List book
     */
    @Query("SELECT * FROM BOOK ORDER BY noOfView DESC")
    LiveData<List<BookEntity>> getBooksSortedByViews();
    /*
    Date 6/3/2024
    List book
     */
    @Query("SELECT * FROM BOOK ORDER BY id")
    LiveData<List<BookEntity>> loadBooksSortedById();
    /*
    Date 6/3/2024
    List book
     */
    @Query("SELECT * FROM BOOK ORDER BY dateUpload DESC")
    LiveData<List<BookEntity>> loadBooksSortedByDate();

    @Query("SELECT * FROM BOOK WHERE TITLE LIKE :key")
    List<BookEntity> searchByTitle(String key);

    @Update
    void updateBook(BookEntity book);

    @Query("SELECT id FROM CATEGORY WHERE name = :name")
    int getCategoryId(String name);

    @Query("SELECT MAX(id) FROM BOOK")
    int getLatestBookId();

    @Query("SELECT id FROM BOOK WHERE title = :title")
    Integer getExistBook(String title);

    @Query("SELECT image FROM BOOK where id = :id")
    byte[] getImage(int id);

    @Query("DELETE FROM BOOK where id = :id")
    void deleteBook(int id);
}
