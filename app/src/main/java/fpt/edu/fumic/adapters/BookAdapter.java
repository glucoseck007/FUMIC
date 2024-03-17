package fpt.edu.fumic.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.fragment.BookFragment;
import fpt.edu.fumic.ui.UpdateBookActivity;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<BookEntity> bookEntities = new ArrayList<>();

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookEntity book = bookEntities.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.imageView.setImageBitmap(ImageToByte.getBitmapFromByteArray(book.getImage()));
    }

    @Override
    public int getItemCount() {
        return bookEntities.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setBooks(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTitle;
        TextView tvAuthor;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewBookCover);
            tvTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvBookAuthor);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), UpdateBookActivity.class);
                    intent.putExtra("title", tvTitle.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
