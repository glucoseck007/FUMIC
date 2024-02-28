package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import fpt.edu.fumic.dao.CategoryDAO;
import fpt.edu.fumic.dao.UserDAO;
import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private FumicDB mFumicDB;
    private CategoryDAO categoryDAO = new CategoryDAO(this);
    private UserDAO userDAO = new UserDAO(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFumicDB = new FumicDB(this);
        categoryDAO.getAllCategory();
        userDAO.getAllUser();
        Log.d("MainActivity", "Database status: " + (mFumicDB.getReadableDatabase() != null ? "Created" : "Not created"));
        Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent_login);
    }
}
