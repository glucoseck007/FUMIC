package fpt.edu.fumic.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.interfaces.BrowseBook;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.BookEntity;

/*
 * luong_123
 */
public class BrowseBookAdapter extends RecyclerView.Adapter<BrowseBookAdapter.VH> {
    private List<BookEntity> bookList = new ArrayList<>();
    private BrowseBook browseBookInterface;

    public void setBrowseBookInterface(BrowseBook browseBookInterface) {
        this.browseBookInterface = browseBookInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<BookEntity> list) {
        this.bookList = list;
        notifyDataSetChanged();
    }

    public void addData(List<BookEntity> list) {
        int size = this.bookList.size();
        this.bookList.addAll(list);
        notifyItemRangeInserted(size, this.bookList.size() - 1);
    }

    public void updateBook(int p, BookEntity book) {
        this.bookList.set(p, book);
        notifyItemChanged(p);
    }

    @NonNull
    @Override
    public BrowseBookAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_browse_book, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseBookAdapter.VH holder, int position) {
        BookEntity book = bookList.get(holder.getAdapterPosition());

        holder.bind(book);
        holder.btnAccept.setOnClickListener(v -> browseBookInterface.onAccept(holder.getAdapterPosition(), book));
        holder.btnRefuse.setOnClickListener(v -> browseBookInterface.onRefuse(holder.getAdapterPosition(), book));



    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private Button btnAccept;
        private Button btnRefuse;

        private ImageView image;

        private TextView  tvStatus;
        private TextView txtTitle;
        private TextView txtDescription;

        @SuppressLint("WrongViewCast")
        public VH(@NonNull View itemView) {
            super(itemView);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnRefuse = itemView.findViewById(R.id.btnRefuse);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            txtTitle = itemView.findViewById(R.id.text_title);
            txtDescription = itemView.findViewById(R.id.text_views);
            image = itemView.findViewById(R.id.image);
        }
        void bind(BookEntity book){
            txtTitle.setText(book.getTitle());
            txtDescription.setText(book.getDescription());
            Glide.with(itemView.getContext())
                    .load(book.getImage())
                    .error(R.drawable.img_coverbookdetail)
                    .into(image);

            switch (book.getStatus()) {

                case 0: {
                    tvStatus.setText("Reject");
                    tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.FireBrick));
                    break;
                }
                case 1: {
                    tvStatus.setText("Approve");
                    tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.Chartreuse));
                    break;
                }
                case 2: {
                    tvStatus.setText("Pending");
                    tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.Chocolate));
                    break;
                }
            }
        }
    }
}
