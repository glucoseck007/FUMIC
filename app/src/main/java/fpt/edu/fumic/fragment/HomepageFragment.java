package fpt.edu.fumic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpt.edu.fumic.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.adapters.BookAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.repository.BookRepository;

public class HomepageFragment extends Fragment {
    private BookRepository bookRepository;
    BookAdapter bookAdapter;
    private RecyclerView recyclerView;
    List<BookEntity> bookList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookRepository = new BookRepository(getActivity().getApplicationContext());
        bookAdapter = new BookAdapter();
        return inflater.inflate(R.layout.activity_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //anh xa
        recyclerView = view.findViewById(R.id.rv_book_list);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "2024-02-29";


        bookList = bookRepository.getBookListAvailable(1, 9, 0);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(bookAdapter);



//        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Book book) {
//                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
//                intent.putExtra("BookDetail", (Serializable) book);
//                getActivity().startActivity(intent);
//            }
//        });

    }
}
