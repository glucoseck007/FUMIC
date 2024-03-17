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

import com.squareup.picasso.Picasso;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.dao.FavouriteDao;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.CategoryEntity;
import fpt.edu.fumic.database.entity.FavouriteEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.repository.FavouriteRepository;
import fpt.edu.fumic.utils.UserInformation;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView cover, back;
    private TextView tvTitle, tvNoOfView, tvRating, tvDescription, tvCategory;
    private BookEntity bookEntity;

    private FavouriteRepository favouriteRepository;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        initView();

        favouriteRepository = new FavouriteRepository(this);

        // Nhận đối tượng BookEntity từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SelectedBook")) {
            bookEntity = (BookEntity) intent.getSerializableExtra("SelectedBook");
            if (bookEntity != null) {
                tvTitle.setText(bookEntity.getTitle());
                tvNoOfView.setText("" + bookEntity.getNoOfView() + " views");
                tvDescription.setText(bookEntity.getDescription());
                cover.setImageBitmap(ImageToByte.getBitmapFromByteArray((bookEntity.getImage())));

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
        tvNoOfView = findViewById(R.id.tv_view);
        tvRating = findViewById(R.id.tv_rating);
        tvDescription = findViewById(R.id.tv_description);
        tvCategory = findViewById(R.id.tv_category);
    }
}
