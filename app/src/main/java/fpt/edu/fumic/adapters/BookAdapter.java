package fpt.edu.fumic.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.MainActivity;
import fpt.edu.fumic.R;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.entity.BookEntity;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.ui.BookDetailActivity;
import fpt.edu.fumic.ui.BookListActivity;
import fpt.edu.fumic.ui.UpdateBookActivity;

/////17/3
public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static List<BookEntity> bookEntities = new ArrayList<>();
    private int viewType;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(BookEntity book);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public BookAdapter() {
    }

    public BookAdapter(int viewType) {
        this.viewType = viewType;
    }

    private OnDeleteItemClickListener onDeleteItemClickListener;
    public static final int TYPE_BOOK_1 = 1;
    public static final int TYPE_BOOK_2 = 2;
    public static final int TYPE_BOOK_3 = 3;

    public interface OnDeleteItemClickListener {
        void onDeleteItemClicked(int position);
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        onDeleteItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (this.viewType == TYPE_BOOK_1) {
            itemView = inflater.inflate(R.layout.book_item, parent, false);
            return new BookViewHolder1(itemView);
        } else if (this.viewType == TYPE_BOOK_2) {
            itemView = inflater.inflate(R.layout.book_item2, parent, false);
            return new BookViewHolder2(itemView);
        } else {
            itemView = inflater.inflate(R.layout.item_book, parent, false);
            return new BookViewHolder3(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookEntity book = bookEntities.get(position);
        if (holder instanceof BookViewHolder1) {
            ((BookViewHolder1) holder).bind(book);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(book);
                    }
                }
            });
        } else if (holder instanceof BookViewHolder2) {
            ((BookViewHolder2) holder).bind(book);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(book);
                    }
                }
            });
        } else if (holder instanceof BookViewHolder3) {
            ((BookViewHolder3) holder).bind(book);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(book);
                    }
                }
            });
        }
    }


    public BookEntity getItemAtPosition(int position) {
        return bookEntities.get(position);
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

    public void clearBooks() {
        bookEntities.clear();
        notifyDataSetChanged();
    }
    class BookViewHolder1 extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtId;

        public BookViewHolder1(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.text_title);
            txtId = itemView.findViewById(R.id.text_id);
        }

        public void bind(BookEntity bookEntity) {
            if (txtTitle != null && txtId != null) {
                txtTitle.setText(bookEntity.getTitle());
                txtId.setText(String.valueOf(bookEntity.getId()));
            }
        }
    }

    class BookViewHolder2 extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtId;
        private ImageView deleteIcon;

        public BookViewHolder2(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.text_title);
            txtId = itemView.findViewById(R.id.text_id);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
            if (deleteIcon != null) {
                deleteIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION &&
                                onDeleteItemClickListener != null) {
                            onDeleteItemClickListener.onDeleteItemClicked(position);
                        }
                    }
                });
            }
        }

        public void bind(BookEntity bookEntity) {
            if (txtTitle != null && txtId != null) {
                txtTitle.setText(bookEntity.getTitle());
                txtId.setText(String.valueOf(bookEntity.getId()));
            }
        }
    }
    static class BookViewHolder3 extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle, tvAuthor;
        public BookViewHolder3(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewBookCover);
            tvTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvBookAuthor);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        BookEntity bookEntity = bookEntities.get(position);

                        Context context = imageView.getContext();
                        Intent intent = new Intent();

                        if (context instanceof MainActivity) {
                            intent = new Intent(context, BookDetailActivity.class);
                        } else if (context instanceof BookListActivity) {
                            intent = new Intent(context, UpdateBookActivity.class);
                        }
                        intent.putExtra("SelectedBook", bookEntity);
                        context.startActivity(intent);
                    }
                }
            });
        }
        public void bind(BookEntity book) {
            tvTitle.setText(book.getTitle());
            imageView.setImageBitmap(ImageToByte.getBitmapFromByteArray(book.getImage()));
        }
    }
//    class BookViewHolder3 extends RecyclerView.ViewHolder {
//        private ImageView imageView;
//        private TextView tvTitle, tvAuthor;
//
//        public BookViewHolder3(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imageViewBookCover);
//            tvTitle = itemView.findViewById(R.id.tvBookTitle);
//            tvAuthor = itemView.findViewById(R.id.tvBookAuthor);
//        }
//
//        public void bind(BookEntity bookEntity) {
//            if (tvTitle != null) {
//                tvTitle.setText(bookEntity.getTitle());
//            }
//            if (imageView != null) {
//                imageView.setImageBitmap(ImageToByte.getBitmapFromByteArray(bookEntity.getImage()));
//            }
//        }
//    }
}

//public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
//
//    private List<BookEntity> bookEntities = new ArrayList<>();
//    private int viewType;
//
//    public BookAdapter() {
//    }
//
//
//    public BookAdapter(int viewType) {
//        this.viewType = viewType;
//    }
//
//
//    private OnDeleteItemClickListener onDeleteItemClickListener;
//    public static final int TYPE_BOOK_1 = 1;
//    public static final int TYPE_BOOK_2 = 2;
//    public static final int TYPE_BOOK_3 = 3;
//
//    public interface OnDeleteItemClickListener {
//        void onDeleteItemClicked(int position);
//    }
//
//    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
//        onDeleteItemClickListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View itemView;
//        if (this.viewType == TYPE_BOOK_1) {
//            itemView = inflater.inflate(R.layout.book_item, parent, false);
//        } else if (this.viewType == TYPE_BOOK_2) {
//            itemView = inflater.inflate(R.layout.book_item2, parent, false);
//        } else {
//            itemView = inflater.inflate(R.layout.item_book, parent, false);
//        }
//        return new BookViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
//        BookEntity book = bookEntities.get(position);
////        holder.bind(book);
//        holder.tvTitle.setText(book.getTitle());
//        holder.imageView.setImageBitmap(ImageToByte.getBitmapFromByteArray(book.getImage()));
//    }
//
//    public BookEntity getItemAtPosition(int position) {
//        return bookEntities.get(position);
//    }
//    @Override
//    public int getItemCount() {
//        return bookEntities.size();
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    public void setBooks(List<BookEntity> bookEntities) {
//        this.bookEntities = bookEntities;
//        notifyDataSetChanged();
//    }
//
//    public void clearBooks() {
//        bookEntities.clear();
//        notifyDataSetChanged();
//    }
//
//    class BookViewHolder extends RecyclerView.ViewHolder {
//        private TextView txtTitle, txtId;
//        private ImageView deleteIcon;
//
//        ImageView imageView;
//        TextView tvTitle;
//        TextView tvAuthor;
//
//        public BookViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtTitle = itemView.findViewById(R.id.text_title);
//            txtId = itemView.findViewById(R.id.text_id);
//            deleteIcon = itemView.findViewById(R.id.delete_icon);
//            if (viewType == TYPE_BOOK_2) {
//                deleteIcon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION &&
//                                onDeleteItemClickListener != null) {
//                            onDeleteItemClickListener.onDeleteItemClicked(position);
//                        }
//                    }
//                });
//            }
//            imageView = itemView.findViewById(R.id.imageViewBookCover);
//            tvTitle = itemView.findViewById(R.id.tvBookTitle);
//            tvAuthor = itemView.findViewById(R.id.tvBookAuthor);
//        }
//
//        public void bind(BookEntity bookEntity) {
//            txtTitle.setText(bookEntity.getTitle());
//            txtId.setText(String.valueOf(bookEntity.getId()));
//        }
//
//    }
//
//
//}
