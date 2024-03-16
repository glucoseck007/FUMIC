package fpt.edu.fumic.database.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.FavouriteEntity;

/*
 * luong_123
 */
@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(FavouriteEntity favourite);

    @Query("SELECT * FROM FAVOURITE " +
            "INNER JOIN BOOK ON FAVOURITE.bookId = BOOK.id " +
            "WHERE FAVOURITE.userId = :userId limit :limit offset :offset")
    List<BookEntity> getFavouriteList(String userId, int limit, int offset );


    @Query("SELECT COUNT(*) FROM FAVOURITE WHERE userId = :userId AND bookId = :bookId")
    int isBookInFavourites(String userId, int bookId);

    @Delete
    void deleteFavourite(FavouriteEntity favourite);

    @Query("DELETE FROM FAVOURITE WHERE bookId = :bookId")
    void deleteBookById(int bookId);
}
