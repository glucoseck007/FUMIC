package fpt.edu.fumic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;

import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.ui.UserProfileActivity;
import fpt.edu.fumic.utils.DateConverterStrDate;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}

