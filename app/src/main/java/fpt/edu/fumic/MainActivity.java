package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import fpt.edu.fumic.dao.CategoryDAO;
import fpt.edu.fumic.dao.UserDAO;
import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.model.Category;
import fpt.edu.fumic.model.User;
import fpt.edu.fumic.ui.UserProfileActivity;

public class MainActivity extends AppCompatActivity {

    private FumicDB mFumicDB;
    private CategoryDAO categoryDAO = new CategoryDAO(this);
    private UserDAO userDAO = new UserDAO(this);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFumicDB = new FumicDB(this);

        userDAO.addUser("user123", "password123", "John Doe", 30, 1, "johndoe@example.com", "1234567890", 2, "enabled");

        categoryDAO.getAllCategory();
        Log.d("MainActivity", "Database status: " + (mFumicDB.getReadableDatabase() != null ? "Created" : "Not created"));


        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            }
        });

    }

}
