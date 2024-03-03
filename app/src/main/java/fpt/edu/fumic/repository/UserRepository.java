package fpt.edu.fumic.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.UserDAO;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.UserEntity;

public class UserRepository {

    private final UserDAO userDAO;

    public UserRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        userDAO = appDatabase.userDAO();
    }


    public void insertUser(UserEntity user) {
        userDAO.insertUser(user);
    }

    public LiveData<List<UserEntity>> getAllUsers() {
        return userDAO.getAllUser();
    }
    public UserEntity getUserById(String username){
        return userDAO.getUserById(username);
    }
    public void updateUser(UserEntity userEntity){
        userDAO.updateUser(userEntity);
    }
}
