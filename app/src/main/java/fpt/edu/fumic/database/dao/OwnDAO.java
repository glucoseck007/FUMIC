package fpt.edu.fumic.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fpt.edu.fumic.database.entity.OwnEntity;

@Dao
public interface OwnDAO {

    @Query("DELETE FROM OWN WHERE bookId = :bookId")
    void deleteRelationship(int bookId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(OwnEntity own);

    @Query("SELECT * FROM OWN WHERE bookId = :bookId AND authorId = :authorId")
    int getRelationship(int bookId, int authorId);

    @Query("SELECT authorId FROM OWN WHERE bookId = :bookId")
    int getAuthorIdWhoOwn(int bookId);

    @Update
    int updateOwn(OwnEntity own);

}
