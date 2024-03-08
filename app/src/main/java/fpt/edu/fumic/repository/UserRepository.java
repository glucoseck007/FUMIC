package fpt.edu.fumic.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.UserDAO;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.UserEntity;
/*
Date : 5/3/2024
*/
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
        return getUserById(username);
    }
    public void deleteUser(UserEntity user) {
        userDAO.deleteUser(user);
    }
    public void updateUser(UserEntity user) {
        userDAO.updateUser(user);
    }
}
