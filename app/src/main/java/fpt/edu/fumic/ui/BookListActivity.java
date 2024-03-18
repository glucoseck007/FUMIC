package fpt.edu.fumic.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.BookAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.repository.ChapterRepository;


public class BookListActivity extends AppCompatActivity {


    private Spinner spinnerSortOptions;
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private BookRepository bookRepository;
    private int categoryId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);


        categoryId = getIntent().getIntExtra("categoryId", -1);



        recyclerViewBooks = findViewById(R.id.recycler_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBooks.setLayoutManager(layoutManager);


        bookAdapter = new BookAdapter(BookAdapter.TYPE_BOOK_1);

        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookEntity book) {
                // Gửi Intent để mở Activity chi tiết sách
                Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
                intent.putExtra("SelectedBook", book);
            }
        });

        recyclerViewBooks.setAdapter(bookAdapter);


        bookRepository = new BookRepository(this);


        fetchBooksByCategoryId(categoryId);


        spinnerSortOptions = findViewById(R.id.spinner_sort_options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortOptions.setAdapter(adapter);


        spinnerSortOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = adapterView.getItemAtPosition(position).toString();
                if (selectedItem.equals(getString(R.string.sort_by_views))) {
                    fetchBooksSortedByViews();
                } else if (selectedItem.equals(getString(R.string.sort_by_date_DESC))) {
                    fetchBooksSortedByDateDESC();
                } else if (selectedItem.equals(getString(R.string.sort_by_id_ASC))) {
                    fetchBooksSortedByDateASC();
                } else if (selectedItem.equals(getString(R.string.sort_by_rating_DESC))) {
                    fetchBooksSortedByRatingDESC();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    private void fetchBooksByCategoryId(int categoryId) {
        if (categoryId != -1) {
            bookRepository.getBooksByCategoryId(categoryId).observe(this, new Observer<List<BookEntity>>() {
                @Override
                public void onChanged(List<BookEntity> books) {
                    bookAdapter.setBooks(books);
                }
            });
        }
    }


    private void fetchBooksSortedByViews() {
        bookRepository.getBooksSortedByViews(categoryId).observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                bookAdapter.setBooks(books);
            }
        });
    }


    private void fetchBooksSortedByDateDESC() {
        bookRepository.getBooksSortedByDateDESC(categoryId).observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                bookAdapter.setBooks(books);
            }
        });
    }


    private void fetchBooksSortedByDateASC() {
        bookRepository.getBooksSortedByDateASC(categoryId).observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                bookAdapter.setBooks(books);
            }
        });
    }


    private void fetchBooksSortedByRatingDESC() {
        bookRepository.getBooksSortedByRatingDESC(categoryId).observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                bookAdapter.setBooks(books);
            }
        });
    }

}
