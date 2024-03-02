package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import fpt.edu.fumic.dao.CategoryDAO;
import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.model.Category;

public class MainActivity extends AppCompatActivity {

    private FumicDB mFumicDB;
    private CategoryDAO categoryDAO = new CategoryDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFumicDB = new FumicDB(this);
        categoryDAO.getAll();
        Log.d("MainActivity", "Database status: " + (mFumicDB.getReadableDatabase() != null ? "Created" : "Not created"));
    }

}
