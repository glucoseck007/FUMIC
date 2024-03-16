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

import fpt.edu.fumic.interfaces.BrowseBook;
import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.BrowseBookAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.repository.BookRepository;

/*
 * luong_123
 */
public class BrowseBookActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private BrowseBookAdapter browseBookAdapter;
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

        loadBook(false);

        ivBack.setOnClickListener(this);

    }

    private void initActivity() {
        recyclerView = findViewById(R.id.recyclerview);
        ivBack = findViewById(R.id.ivBack);


        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        browseBookAdapter = new BrowseBookAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(browseBookAdapter);

        browseBookAdapter.setBrowseBookInterface(new BrowseBook() {
            @Override
            public void onAccept(int p, BookEntity book) {
                book.setStatus(1);
                bookRepository.updateBook(book);
                browseBookAdapter.updateBook(p, book);

            }

            @Override
            public void onRefuse(int p, BookEntity book) {
                book.setStatus(0);
                bookRepository.updateBook(book);
                browseBookAdapter.updateBook(p, book);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                if (dy > 0) {
                    if (!isLoading && !isLastItemReached) {
                        if (totalItemCount - 1 == lastVisibleItemPosition) {
                            Log.d("__note", "load more");
                            loadBook(true);
                        }
                    }
                }
            }
        });

    }


    private void loadBook(boolean isLoadMore) {
        isLoading = true;
        runOnUiThread(() -> {
            List<BookEntity> list = bookRepository.getBookListAvailable(2, 10, offset);
            offset += list.size();
            isLoading = false;
            isLastItemReached = list.size() < 10;
            if (!isLoadMore) {
                browseBookAdapter.setData(list);
            } else {
                browseBookAdapter.addData(list);
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            finish();
        }
    }
}