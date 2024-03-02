package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import fpt.edu.fumic.dao.CategoryDAO;
import fpt.edu.fumic.database.FumicDB;

public class MainActivity extends AppCompatActivity {

    private FumicDB mFumicDB;
    private final CategoryDAO categoryDAO = new CategoryDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFumicDB = new FumicDB(this);
        categoryDAO.getAllCategory();
        Log.d("MainActivity", "Database status: " + (mFumicDB.getReadableDatabase() != null ? "Created" : "Not created"));
    }
}
