package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.util.Date;

import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.ui.UserProfileActivity;
import fpt.edu.fumic.utils.DateConverterStrDate;


public class MainActivity extends AppCompatActivity {

    UserRepository userRepository;
    BookRepository bookRepository;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userRepository = new UserRepository(this);
        bookRepository = new BookRepository(this);
        Date xDob;
        try {
            xDob = DateConverterStrDate.stringToDate("2022-03-03");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



//        //insert books
//
//        for (int i = 0; i <= 15; i++) {
//            bookRepository.createBook(new BookEntity(i,"haha " + i, 1, "haa", "haha", 1, 1, 1, xDob, 2, 123));
//            Log.d("__add", "onCreate: " + i + " ");
//        }
//
//        //insert user
//        userRepository.insertUser(new UserEntity("luong111", "123123", "Luong QQ", xDob, 1, "luong@gmail.com", "012312311", 0));


        findViewById(R.id.btnProfile).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this, UserProfileActivity.class))
        );

    }
}
