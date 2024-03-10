package fpt.edu.fumic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpt.edu.fumic.adapters.BookMainAdapter;
import fpt.edu.fumic.R;
import java.io.Serializable;
import java.util.List;

import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.repository.BookRepository;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private Button btSearch, btCancel;
    private RecyclerView recyclerView;
    private BookRepository db;
    private BookMainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        //Button cancel
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String key) {
                List<BookEntity> list = db.searchByTitle(key);
                adapter = new BookMainAdapter(getApplicationContext(), list);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        //Button search
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new BookMainAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BookEntity book) {
                        Intent intent = new Intent(SearchActivity.this, BookDetailActivity.class);
                        intent.putExtra("BookDetail", (Serializable) book);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    public void initView(){
        searchView = findViewById(R.id.sv_search);
        btSearch = findViewById(R.id.bt_search);
        btCancel = findViewById(R.id.bt_cancel);
        db = new BookRepository(getApplicationContext());
        recyclerView = findViewById(R.id.rv_search_list);
    }
}