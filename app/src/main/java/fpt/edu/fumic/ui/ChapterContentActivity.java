package fpt.edu.fumic.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Objects;

import fpt.edu.fumic.MainActivity;
import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.repository.ChapterRepository;

public class ChapterContentActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvChapterNumber, tvChapterContent;
    private Button btBack, btHome;
    ChapterRepository repository;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        initView();
        repository = new ChapterRepository(this);

        btHome.setOnClickListener(this);
        btBack.setOnClickListener(this);

        //show chapter content
        Intent intent = getIntent();
        int chapterNo = intent.getIntExtra("ChapterNo", 1);
        int bookId = intent.getIntExtra("BookId", 1);
        String chapterTitle = "Chapter " + chapterNo + ": " + intent.getStringExtra("ChapterTitle");
        String content = repository.getChapterPerContent(bookId, chapterNo);
        tvChapterNumber.setText(chapterTitle);
        tvChapterContent.setText(standardizeData(content));
    }
    public void initView() {
        tvChapterNumber = findViewById(R.id.tv_title);
        tvChapterContent = findViewById(R.id.tv_content);
        btBack = findViewById(R.id.bt_back);
        btHome = findViewById(R.id.bt_home);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_home) {
            Intent intentHome = new Intent(ChapterContentActivity.this, MainActivity.class);
            startActivity(intentHome);
        } else if (v.getId() == R.id.bt_back) {
            finish();
        }
    }

    private static String standardizeData(String data) {
        return data.replace("|||", "\n");
    }
}