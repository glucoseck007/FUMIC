package fpt.edu.fumic.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Objects;

import fpt.edu.fumic.MainActivity;
import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.repository.ChapterRepository;

public class ChapterContentActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvChapterNumber, tvChapterContent;
    private ImageView ivPre, ivNext;
    ChapterRepository repository;
    ScrollView scrollView;
    private int bookId, chapterNo, noOfChapters;
    private String chapterTitle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        initView();
        repository = new ChapterRepository(this);
        scrollView = findViewById(R.id.chapterContent);

        ivPre.setOnClickListener(this);
        ivNext.setOnClickListener(this);

        //show chapter content
        Intent intent = getIntent();
        chapterNo = intent.getIntExtra("ChapterNo", 1);
        bookId = intent.getIntExtra("BookId", 1);
        chapterTitle = "Chapter " + chapterNo + ": " + repository.getChapterTitleByChapterNo(chapterNo);
        noOfChapters = intent.getIntExtra("noOfChapters", 1);
        String content = repository.getChapterPerContent(bookId, chapterNo);
        tvChapterNumber.setText(chapterTitle);
        tvChapterContent.setText(standardizeData(content));
    }
    public void initView() {
        tvChapterNumber = findViewById(R.id.tv_title);
        tvChapterContent = findViewById(R.id.tv_content);
        ivPre = findViewById(R.id.iv_previous);
        ivNext = findViewById(R.id.iv_next);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_previous) {
            if (chapterNo == 1) finish();
            else {
                --chapterNo;
                chapterTitle = "Chapter " + chapterNo + ": " + repository.getChapterTitleByChapterNo(chapterNo);
                String content = repository.getChapterPerContent(bookId, chapterNo);
                tvChapterNumber.setText(chapterTitle);
                tvChapterContent.setText(standardizeData(content));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, 0);
                    }
                }, 100);
            }
        } else if (v.getId() == R.id.iv_next) {
            if (chapterNo == noOfChapters) finish();
            else {
                ++chapterNo;
                chapterTitle = "Chapter " + chapterNo + ": " + repository.getChapterTitleByChapterNo(chapterNo);
                String content = repository.getChapterPerContent(bookId, chapterNo);
                tvChapterNumber.setText(chapterTitle);
                tvChapterContent.setText(standardizeData(content));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, 0);
                    }
                }, 100);
            }
        }
    }

    private static String standardizeData(String data) {
        return data.replace("|||", "\n");
    }
}