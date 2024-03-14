package fpt.edu.fumic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import fpt.edu.fumic.MainActivity;
import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.ChapterEntity;

public class ChapterContentActivity extends AppCompatActivity {
    private TextView tvChapterNumber, tvChapterContent;
    private Button btBack, btHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        initView();
        //set button
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(ChapterContentActivity.this, MainActivity.class);
                startActivity(intentHome);
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //show chapter content
        Intent intent = getIntent();
        ChapterEntity chapter = (ChapterEntity) intent.getSerializableExtra("ChapterContent");
        tvChapterNumber.setText(chapter.getChapterNumber());
        tvChapterContent.setText(chapter.getChapterContent());
    }
    public void initView(){
        tvChapterNumber = findViewById(R.id.tv_title);
        tvChapterContent = findViewById(R.id.tv_content);
        btBack = findViewById(R.id.bt_back);
        btHome = findViewById(R.id.bt_home);
    }
}