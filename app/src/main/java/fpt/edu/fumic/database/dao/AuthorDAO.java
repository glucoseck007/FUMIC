package fpt.edu.fumic.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fpt.edu.fumic.database.entity.AuthorEntity;

@Dao
public interface AuthorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAuthor(AuthorEntity author);

    @Query("SELECT MAX(id) FROM AUTHOR")
    int getLatestAuthorId();

    @Query("SELECT id FROM AUTHOR where name = :name")
    Integer getExistAuthor(String name);

    @Query("DELETE FROM AUTHOR where id = :id")
    void deleteAuthor(int id);

    @Query("SELECT name FROM AUTHOR where id = :id")
    String getAuthorById(int id);
}
