package fpt.edu.fumic.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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

    public ArrayList<User> getAllUser() {
        String sql = "SELECT * FROM USER";
        return get(sql);
    }



    public void addUser(String id, String password, String name, int age, int gender, String email, String phone, int role, String notification) {
        SQLiteDatabase db = fumicDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Id", id);
        values.put("Password", password);
        values.put("Name", name);
        values.put("Age", age);
        values.put("Gender", gender);
        values.put("Email", email);
        values.put("Phone", phone);
        values.put("Role", role);
        values.put("Notification", notification);

        long result = db.insert("USER", null, values);
        if (result == -1) {
            Log.e("DB_ERROR", "Failed to insert data");
        } else {
            Log.i("DB_SUCCESS", "User inserted successfully");
        }
        db.close();
    }

    public User getUser(String id) {
        SQLiteDatabase db = fumicDB.getWritableDatabase();

        String[] projection = {
                "Id",
                "Password",
                "Name",
                "Age",
                "Gender",
                "Email",
                "Phone",
                "Role",
                "Notification"
        };

        String selection = "Id = ?";
        String[] selectionArgs = {id};

        Cursor cursor = db.query(
                "USER",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        User user = new User();

        if (cursor != null && cursor.moveToFirst()) {
            String uid = cursor.getString(cursor.getColumnIndexOrThrow("Id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow("Age"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("Phone"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("Password"));
            int gender = cursor.getInt(cursor.getColumnIndexOrThrow("Gender"));
            int role = cursor.getInt(cursor.getColumnIndexOrThrow("Role"));
            String notification = cursor.getString(cursor.getColumnIndexOrThrow("Notification"));


            user.setId(uid);
            user.setName(name);
            user.setAge(age);
            user.setPhone(phone);
            user.setEmail(email);
            user.setPassword(password);
            user.setGender(gender);
            user.setRole(role);
            user.setNotification(notification);


            cursor.close();
        }
        return user;
    }

    public boolean updateUser(String id, String password, String name, int age, int gender, String email, String phone, int role, String notification) {
        SQLiteDatabase db = fumicDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password", password);
        contentValues.put("Name", name);
        contentValues.put("Age", age);
        contentValues.put("Gender", gender);
        contentValues.put("Email", email);
        contentValues.put("Phone", phone);
        contentValues.put("Role", role);
        contentValues.put("Notification", notification);
        int updateStatus = db.update("USER", contentValues, "Id = ?", new String[]{id});
        db.close();
        return updateStatus > 0;
    }

    public boolean changePassword(String userId, String newPassword) {
        SQLiteDatabase db = fumicDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password", newPassword);
        int updateStatus = db.update("USER", contentValues, "Id = ?", new String[]{userId});
        db.close();
        return updateStatus > 0;
    }

}
