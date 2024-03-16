package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.util.Date;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.fragments.BookFragment;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.ui.AddBookActivity;
import fpt.edu.fumic.ui.RegisterActivity;
import fpt.edu.fumic.ui.UserProfileActivity;
import fpt.edu.fumic.utils.DateConverterStrDate;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btnProfile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddBookActivity.class));
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new BookFragment())
                .commit();
    }
}
