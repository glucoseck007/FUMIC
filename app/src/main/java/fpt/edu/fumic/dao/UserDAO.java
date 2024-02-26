package fpt.edu.fumic.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.model.User;

public class UserDAO {
    FumicDB fumicDB;

    public UserDAO(FumicDB fumicDB) {
        this.fumicDB = fumicDB;
    }
    public UserDAO(Context context) {
        this.fumicDB = new FumicDB(context);
    }
    @SuppressLint("Range")
    public ArrayList<User> get(String sql, String... choose) {
        ArrayList<User> userArrayList = new ArrayList<>();
        SQLiteDatabase db = fumicDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, choose);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                User user = new User();
                user.setId(cursor.getString(cursor.getColumnIndex("Id")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("Password")));
                user.setName(cursor.getString(cursor.getColumnIndex("Name")));
                user.setAge(cursor.getInt(cursor.getColumnIndex("Age")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                user.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));
                user.setRole(cursor.getInt(cursor.getColumnIndex("Role")));
                user.setNotification(cursor.getString(cursor.getColumnIndex("Notification")));
                userArrayList.add(user);
                Log.i("TAG", user.getName());
            } while (cursor.moveToNext());
        }
        return userArrayList;
    }
    public ArrayList<User> getAllUser(){
        String sql = "SELECT * FROM USER";
        return get(sql);
    }
}
