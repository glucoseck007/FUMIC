package fpt.edu.fumic.fragment;

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
import fpt.edu.fumic.database.dao.CategoryDAO;
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

        RecyclerView recyclerView1 = rootView.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView2 = rootView.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new BookAdapter();
        recyclerView1.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);

        viewModel.getAllBooks().observe(getViewLifecycleOwner(), bookEntities -> {
            adapter.setBooks(bookEntities);
        });

        return rootView;
    }

}
