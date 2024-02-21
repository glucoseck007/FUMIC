package fpt.edu.fumic.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.model.Category;

public class CategoryDAO {

    FumicDB fumicDB;

    public CategoryDAO(Context context) {this.fumicDB = new FumicDB(context); }

    @SuppressLint("Range")
    public ArrayList<Category> get(String sql, String... choose) {
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        SQLiteDatabase db = fumicDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, choose);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                category.setName(cursor.getString(cursor.getColumnIndex("Name")));
                categoryArrayList.add(category);
                Log.i("TAG", category.getName());
            } while (cursor.moveToNext());
        }
        return categoryArrayList;
    }

    public ArrayList<Category> getAll() {
        String sql = "SELECT * FROM CATEGORY";

        return get(sql);
    }

}
