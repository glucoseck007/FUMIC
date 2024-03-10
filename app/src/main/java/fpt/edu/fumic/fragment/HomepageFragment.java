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

import com.example.prm_project.BookDetailActivity;
import com.example.prm_project.R;
import com.example.prm_project.adapter.BookAdapter;
import com.example.prm_project.dal.BookDatabase;
import com.example.prm_project.model.Book;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomepageFragment extends Fragment {
    private BookAdapter adapter;
    private BookDatabase db;
    private RecyclerView recyclerView;
    List<Book> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //anh xa
        db = new BookDatabase(getContext());
        recyclerView = view.findViewById(R.id.rv_book_list);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "2024-02-29";
        try {
            Date dateUpload = dateFormat.parse(dateString);
            Book book1 = new Book("Book 1", 10, "Des 1", 1, 5, 100,
                    dateUpload, 1, "User 1", "https://upload.wikimedia.org/wikipedia/vi/f/f2/Owari_no_Seraph.jpeg");
            Book book2 = new Book("Book 2", 11, "Des 2", 1, 5, 100,
                    dateUpload, 1, "User 2", "https://static.wikia.nocookie.net/owarinoseraph/images/1/15/Volume_2_%28English%29.png/revision/latest?cb=20190131021507");
            db.addBook(book1);
            db.addBook(book2);
        }catch (ParseException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình chuyển đổi
            e.printStackTrace();
        }

        list = db.getAll();
        adapter = new BookAdapter(getContext(), list);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("BookDetail", (Serializable) book);
                getActivity().startActivity(intent);
            }
        });

    }
}
