package fpt.edu.fumic.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.BookEntity;

/*
Date 6/3/2024
List book
 */
/*
 * luong_123
 */
public class HistoriesAdapter extends RecyclerView.Adapter<HistoriesAdapter.BookViewHolder> {

    private List<BookEntity> books = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setBooks(List<BookEntity> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public void addBooks(List<BookEntity> books) {
        int size = this.books.size();
        this.books.addAll(books);
        notifyItemRangeInserted(size, this.books.size() - 1);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookEntity currentBook = books.get(position);
        holder.bind(currentBook);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtDescription;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.text_title);
            txtDescription = itemView.findViewById(R.id.text_views);
        }

        public void bind(BookEntity book) {
            txtTitle.setText(book.getTitle());
            txtDescription.setText(book.getDescription());
            // You can bind more data if needed
        }
    }
}

