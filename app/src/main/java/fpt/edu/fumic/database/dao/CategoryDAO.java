package fpt.edu.fumic.database.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.database.entity.CategoryEntity;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM CATEGORY")
    LiveData<List<CategoryEntity>> loadAllCategories();

}
