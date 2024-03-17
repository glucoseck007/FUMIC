package fpt.edu.fumic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.BookAdapter;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.ui.BookDetailActivity;
import fpt.edu.fumic.ui.BookListActivity2;
import fpt.edu.fumic.viewmodel.BookViewModel;

public class BookFragment extends Fragment {

    private BookViewModel viewModel;
    private BookAdapter adapter;

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BookViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        //dùng adapter với layout item_book
        adapter = new BookAdapter(BookAdapter.TYPE_BOOK_3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

//        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookEntity book) {
                // Gửi Intent để mở Activity chi tiết sách
                Intent intent = new Intent(requireActivity(), BookDetailActivity.class);
                intent.putExtra("SelectedBook", book);
                startActivity(intent);
            }
        });
        viewModel.getAllBooks().observe(getViewLifecycleOwner(), bookEntities -> {
            adapter.setBooks(bookEntities);
        });

        return rootView;
    }

}
