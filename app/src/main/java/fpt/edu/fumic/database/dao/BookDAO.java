package fpt.edu.fumic.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fpt.edu.fumic.database.entity.BookEntity;

@Dao
public interface BookDAO {
    @Insert
    void insertAllBooks(List<BookEntity> bookEntities);

    @Insert
    void insertBook(BookEntity book);

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

    @Update
    void updateBook(BookEntity book);
}
