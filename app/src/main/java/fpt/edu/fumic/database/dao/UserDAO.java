package fpt.edu.fumic.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import fpt.edu.fumic.database.entity.UserEntity;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM USER")
    LiveData<List<UserEntity>> getAllUser();
}
