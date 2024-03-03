package fpt.edu.fumic.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.interfaces.BrowseBook;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.BookEntity;


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

        holder.tvTitle.setText(book.getTitle());
        switch (book.getStatus()) {

            case 0: {
                holder.tvStatus.setText("Đã từ chối");
                holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.FireBrick));
                break;
            }
            case 1: {
                holder.tvStatus.setText("Đã duyệt");
                holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Chartreuse));
                break;
            }
            case 2: {
                holder.tvStatus.setText("Đang chờ duyệt");
                holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Chocolate));
                break;
            }
        }

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

        private TextView tvTitle, tvStatus;

        @SuppressLint("WrongViewCast")
        public VH(@NonNull View itemView) {
            super(itemView);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnRefuse = itemView.findViewById(R.id.btnRefuse);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
