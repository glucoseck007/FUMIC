package fpt.edu.fumic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import fpt.edu.fumic.adapters.HistoriesAdapter;
import fpt.edu.fumic.interfaces.BrowseBook;
import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.BrowseBookAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.repository.BookRepository;
/*
 * luong_123
 */
public class HistoriesActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private HistoriesAdapter historiesAdapter;
    private BookRepository bookRepository;
    private LinearLayoutManager layoutManager;
    private ImageView ivBack;

    private int offset = 0;
    private boolean isLoading = false;
    private boolean isLastItemReached = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_book);

        bookRepository = new BookRepository(this);

        initActivity();

        loadBook();

        ivBack.setOnClickListener(this);

    }

    private void initActivity() {
        recyclerView = findViewById(R.id.recyclerview);
        ivBack = findViewById(R.id.ivBack);


        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        historiesAdapter = new HistoriesAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historiesAdapter);




    }


    private void loadBook() {
        isLoading = true;
        runOnUiThread(() -> {
            List<BookEntity> list = bookRepository.getBookListAvailable(1, 3, offset);
            offset += list.size();
            isLoading = false;
            isLastItemReached = list.size() < 3;
            historiesAdapter.setBooks(list);
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            finish();
        }
    }
}