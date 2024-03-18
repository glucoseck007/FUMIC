package fpt.edu.fumic.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.BookAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.utils.AlertDialog1;


public class BookListActivity2 extends AppCompatActivity {


    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private BookRepository repository;
    private TextView tvempty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list2);


        recyclerView = findViewById(R.id.recycler_view);
        adapter = new BookAdapter(BookAdapter.TYPE_BOOK_2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvempty = findViewById(R.id.empty_text_view);

        repository = new BookRepository(this);


        adapter.setOnDeleteItemClickListener(new BookAdapter.OnDeleteItemClickListener() {
            @Override
            public void onDeleteItemClicked(int position) {
                BookEntity bookToDelete = adapter.getItemAtPosition(position);
                deleteBook(bookToDelete);
            }
        });

        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookEntity book) {
                // Gửi Intent để mở Activity chi tiết sách
                Intent intent = new Intent(BookListActivity2.this, UpdateBookActivity.class);
                intent.putExtra("SelectedBook", book);
                intent.putExtra("title", book.getTitle());
                startActivity(intent);
            }
        });
        Button acceptButton = findViewById(R.id.accept_button);
        Button rejectButton = findViewById(R.id.reject_button);
        Button pendingButton = findViewById(R.id.pending_button);


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveData<List<BookEntity>> booksLiveData = repository.getBooksByStatus(1);
                observeBooks(booksLiveData);
            }
        });


        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveData<List<BookEntity>> booksLiveData = repository.getBooksByStatus(0);
                observeBooks(booksLiveData);
            }
        });


        pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookListActivity2.this, BrowseBookActivity.class);
                startActivity(intent);
            }
        });
        loadBooksByStatus(1);
    }


    private void loadBooksByStatus(int status) {
        LiveData<List<BookEntity>> booksLiveData = repository.getBooksByStatus(status);
        observeBooks(booksLiveData);
    }




    private void observeBooks(LiveData<List<BookEntity>> booksLiveData) {
        booksLiveData.observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> bookEntities) {
                if (bookEntities.isEmpty()) {
                    tvempty.setVisibility(View.VISIBLE);
                    tvempty.setText("Have 0 book");
                    adapter.clearBooks();
                } else {
                    tvempty.setVisibility(View.GONE);
                    adapter.setBooks(bookEntities);
                }
            }
        });
    }


    private void deleteBook(BookEntity book) {
        AlertDialog1.showConfirmationDialog(this, "Confirm Deletion", "Are you sure want to delete that book?",
                new AlertDialog1.OnConfirmationListener() {
                    @Override
                    public void onConfirm() {
                        repository.deleteRelationship(book.getId());
                        repository.deleteChapterByBookId(book.getId());
                        repository.deleteFavouriteBookById(book.getId());
                        repository.deleteReadByBookId(book.getId());
                        repository.deleteBook(book);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }


}
