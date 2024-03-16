package fpt.edu.fumic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import fpt.edu.fumic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.entity.BookEntity;

public class BookMainAdapter extends RecyclerView.Adapter<BookMainAdapter.ViewHolder> {
    private Context context;
    private List<BookEntity> list = new ArrayList<>();

    public List<BookEntity> getList() {
        return list;
    }

    public void setList(List<BookEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public BookMainAdapter(Context context, List<BookEntity> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView cover;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            cover = itemView.findViewById(R.id.iv_cover);
            cardView = itemView.findViewById(R.id.cv_book);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookEntity book = list.get(position);
        holder.title.setText(book.getTitle());
        holder.cover.setImageBitmap(ImageToByte.getBitmapFromByteArray(book.getImage()));;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(book);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface OnItemClickListener {
        void onItemClick(BookEntity book);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
