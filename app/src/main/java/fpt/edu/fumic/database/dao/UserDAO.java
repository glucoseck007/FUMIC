package fpt.edu.fumic.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

import fpt.edu.fumic.database.entity.UserEntity;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM USER")
    ArrayList<UserEntity> getAllUser();
}
