package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD

import fpt.edu.fumic.database.dao.CategoryDAO;
import fpt.edu.fumic.database.FumicDB;
=======
import fpt.edu.fumic.dao.CategoryDAO;
import fpt.edu.fumic.database.FumicDB;

>>>>>>> e6782aae32579da83e2f8654fab8f7eace06fcf0

public class MainActivity extends AppCompatActivity {

    private FumicDB mFumicDB;
<<<<<<< HEAD
=======
    private final CategoryDAO categoryDAO = new CategoryDAO(this);
>>>>>>> e6782aae32579da83e2f8654fab8f7eace06fcf0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFumicDB = new FumicDB(this);
<<<<<<< HEAD
=======
        categoryDAO.getAllCategory();
>>>>>>> e6782aae32579da83e2f8654fab8f7eace06fcf0
        Log.d("MainActivity", "Database status: " + (mFumicDB.getReadableDatabase() != null ? "Created" : "Not created"));
    }

}
