package fpt.edu.fumic.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.adapters.ChapterAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.model.Book;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.repository.ChapterRepository;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView cover, back;
    private TextView tvTitle, tvNoOfView, tvRating, tvDescription, tvDateUpload;
    ChapterRepository chapterRepository;
    ChapterAdapter chapterAdapter;
    BookRepository bookRepository;
    RecyclerView recyclerView;
    List<ChapterEntity> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        initView();
        bookRepository = new BookRepository(this);
        chapterRepository = new ChapterRepository(this);
        chapterAdapter = new ChapterAdapter();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        BookEntity book = bookRepository.getBookByTitle(title);

        tvTitle.setText(book.getTitle());
        tvNoOfView.setText(""+book.getNoOfView()+" views");
        tvDescription.setText(book.getDescription());
        tvDateUpload.setText(book.getDateUpload().toString());
        cover.setImageBitmap(ImageToByte.getBitmapFromByteArray((book.getImage())));

        tvRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //recycler view chapter list
//        list = chapterRepository.getChaptersByBookId(book.getId());
//        chapterAdapter.setList(list);
//        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(chapterAdapter);
//        chapterAdapter.setOnItemClickListener(new ChapterAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(ChapterEntity chapter) {
//                Intent intent1 = new Intent(BookDetailActivity.this, ChapterContentActivity.class);
//                intent1.putExtra("ChapterContent", (Serializable) chapter);
//                startActivity(intent1);
//            }
//        });
    }

    public void initView(){
        cover = findViewById(R.id.iv_cover);
        back = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvNoOfView = findViewById(R.id.tv_view);
        tvRating = findViewById(R.id.tv_rating);
        tvDescription = findViewById(R.id.tv_description);
        recyclerView = findViewById(R.id.rv_chapter);
        tvDateUpload = findViewById(R.id.tv_date);
    }
}