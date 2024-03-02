package fpt.edu.fumic.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
}
