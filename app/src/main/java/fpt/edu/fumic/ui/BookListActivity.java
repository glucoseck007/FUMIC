package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.BookAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.repository.BookRepository;
/*
Date 6/3/2024
List book
 */
public class BookListActivity extends AppCompatActivity {

    private Spinner spinnerSortOptions;
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private BookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);


        spinnerSortOptions = findViewById(R.id.spinner_sort_options); // Find the spinner
        recyclerViewBooks = findViewById(R.id.recycler_view_books);

        // Initialize BookRepository
        bookRepository = new BookRepository(this);

        // Populate spinner with sorting options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortOptions.setAdapter(adapter);

        // Set spinner item selection listener
        spinnerSortOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = adapterView.getItemAtPosition(position).toString();
                // Fetch books based on selected sorting option
                if (selectedItem.equals(getString(R.string.sort_by_views))) {
                    fetchBooksSortedByViews();
                } else if (selectedItem.equals(getString(R.string.sort_by_date))) {
                    fetchBooksSortedByDate();
                } else if (selectedItem.equals(getString(R.string.sort_by_id))) {
                    fetchBooksSortedById();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Set up RecyclerView and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBooks.setLayoutManager(layoutManager);
        bookAdapter = new BookAdapter();
        recyclerViewBooks.setAdapter(bookAdapter);
    }

    private void fetchBooksSortedByViews() {
        bookRepository.getBooksSortedByViews().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                bookAdapter.setBooks(books);
            }
        });
    }

    private void fetchBooksSortedByDate() {
        bookRepository.getBooksSortedByDate().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                bookAdapter.setBooks(books);
            }
        });
    }

    private void fetchBooksSortedById() {
        bookRepository.getBooksSortedById().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                bookAdapter.setBooks(books);
            }
        });
    }
}
