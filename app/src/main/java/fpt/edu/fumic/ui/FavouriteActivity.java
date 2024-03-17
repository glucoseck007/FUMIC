package fpt.edu.fumic.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.FavouriteAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.interfaces.FavouriteBook;
import fpt.edu.fumic.repository.FavouriteRepository;
import fpt.edu.fumic.utils.UserInformation;

/*
 * luong_123
 */
public class FavouriteActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private FavouriteAdapter favouriteAdapter;
    private FavouriteRepository favouriteRepository;
    private LinearLayoutManager layoutManager;
    private ImageView ivBack;

    private int offset = 0;
    private boolean isLoading = false;
    private boolean isLastItemReached = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        favouriteRepository = new FavouriteRepository(this);

        initActivity();

        loadBook(false);

        ivBack.setOnClickListener(this);

    }

    private void initActivity() {
        recyclerView = findViewById(R.id.recyclerview);
        ivBack = findViewById(R.id.ivBack);


        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        favouriteAdapter = new FavouriteAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favouriteAdapter);

        favouriteAdapter.setFavouriteBook(new FavouriteBook() {
            @Override
            public void onDelete(int p, BookEntity book) {
                favouriteRepository.deleteFavourite(book.getId());
                favouriteAdapter.deleteView(p);
            }

            @Override
            public void onClick(int p, BookEntity book) {
                Intent intent = new Intent(FavouriteActivity.this, BookDetailActivity.class);
                intent.putExtra("bookId", book.getId());
                mStartForStoryResult.launch(intent);
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

    ActivityResultLauncher<Intent> mStartForStoryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    loadBook(false);
                }
            });


    private void loadBook(boolean isLoadMore) {
        isLoading = true;
        runOnUiThread(() -> {
            List<BookEntity> list = favouriteRepository.getFavourite(UserInformation.getInstance().getUser().getId(), 10, offset);
            offset += list.size();
            isLoading = false;
            isLastItemReached = list.size() < 10;
            if (!isLoadMore) {
                favouriteAdapter.setBooks(list);
            } else {
                favouriteAdapter.addBooks(list);
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