package fpt.edu.fumic.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.repository.BookRepository;

public class BookViewModel extends AndroidViewModel {

    private final LiveData<List<BookEntity>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);

        BookRepository repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
    }

    public LiveData<List<BookEntity>> getAllBooks() { return allBooks; }

}
