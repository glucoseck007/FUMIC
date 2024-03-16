package fpt.edu.fumic.database.dao;

import android.app.UiAutomation;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fpt.edu.fumic.database.entity.UserEntity;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM USER")
    LiveData<List<UserEntity>> getAllUser();

    @Query("SELECT * FROM USER WHERE Id = :username")
    UserEntity getUserById(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Update
    void updateUser(UserEntity userEntity);
    /*
    Date : 5/3/2024
    */
    @Delete
    void deleteUser(UserEntity user);
}
