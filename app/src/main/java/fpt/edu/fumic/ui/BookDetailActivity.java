package fpt.edu.fumic.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.model.Book;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView cover, back;
    private TextView tvTitle, tvNoOfView, tvRating, tvDescription;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        initView();
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("BookDetail");
        assert book != null;
        tvTitle.setText(book.getTitle());
        tvNoOfView.setText(""+book.getNoOfView()+" views");
        tvDescription.setText(book.getDescription());
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
    }

    public void initView(){
        cover = findViewById(R.id.iv_cover);
        back = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvNoOfView = findViewById(R.id.tv_view);
        tvRating = findViewById(R.id.tv_rating);
        tvDescription = findViewById(R.id.tv_description);
    }
}