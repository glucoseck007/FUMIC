package fpt.edu.fumic.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fpt.edu.fumic.database.entity.OwnEntity;

@Dao
public interface OwnDAO {

    @Insert
    long insert(OwnEntity own);

    @Query("SELECT * FROM OWN WHERE bookId = :bookId AND authorId = :authorId")
    int getRelationship(int bookId, int authorId);

}
