package fpt.edu.fumic.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.ChapterAdapter;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.dao.FavouriteDao;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.CategoryEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.entity.FavouriteEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.repository.ChapterRepository;
import fpt.edu.fumic.repository.FavouriteRepository;
import fpt.edu.fumic.utils.MyToast;
import fpt.edu.fumic.utils.UserInformation;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView cover, back;
    private TextView tvTitle, tvNoOfView, tvRating, tvDescription, tvCategory;
    private BookEntity bookEntity;
    RecyclerView recyclerView;
    ChapterRepository chapterRepository;
    ChapterAdapter chapterAdapter;
    List<ChapterEntity> list = new ArrayList<ChapterEntity>();


    private FavouriteRepository favouriteRepository;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        initView();

        favouriteRepository = new FavouriteRepository(this);

        chapterRepository = new ChapterRepository(this);
        chapterAdapter = new ChapterAdapter();

        // Nhận đối tượng BookEntity từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SelectedBook")) {
            bookEntity = (BookEntity) intent.getSerializableExtra("SelectedBook");
            if (bookEntity != null) {
                tvTitle.setText(bookEntity.getTitle());
                tvNoOfView.setText("" + bookEntity.getNoOfView() + " views");
                tvDescription.setText(bookEntity.getDescription());
                cover.setImageBitmap(ImageToByte.getBitmapFromByteArray((bookEntity.getImage())));

                list = chapterRepository.getChapterTitleById(bookEntity.getId());
                chapterAdapter.setList(list);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(chapterAdapter);
                chapterAdapter.setOnItemClickListener(new ChapterAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ChapterEntity chapter) {
                        Intent intent1 = new Intent(BookDetailActivity.this, ChapterContentActivity.class);
                        intent1.putExtra("chapterNo", chapter.getChapterNo());
                        intent1.putExtra("chapterTitle", chapter.getChapterTitle());
                        intent1.putExtra("bookId", chapter.getBookId());
                        intent1.putExtra("noOfChapters", list.size());
                        startActivity(intent1);
                    }
                });
            }

                // Lấy thông tin danh mục từ categoryId
                BookRepository bookRepository = new BookRepository(this);
                bookRepository.getCategoryById(bookEntity.getCategoryId()).observe(this, new Observer<CategoryEntity>() {
                    @Override
                    public void onChanged(CategoryEntity categoryEntity) {
                        if (categoryEntity != null) {
                            tvCategory.setText(categoryEntity.getName());
                        }
                    }
                });
            }

        tvRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện đánh giá
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.cb_favourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookEntity != null){
                    MyToast.confusingToast(BookDetailActivity.this, "Added successfully");
                    FavouriteEntity favourite = new FavouriteEntity(UserInformation.getInstance().getUser().getId(),bookEntity.getId());
                    favouriteRepository.addFavourite(favourite);
                }else {
                    Toast.makeText(BookDetailActivity.this, "Null", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void initView() {
        cover = findViewById(R.id.iv_cover);
        back = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        recyclerView = findViewById(R.id.rv_chapter);
        tvNoOfView = findViewById(R.id.tv_view);
        tvRating = findViewById(R.id.tv_rating);
        tvDescription = findViewById(R.id.tv_description);
        tvCategory = findViewById(R.id.tv_category);
    }
}
