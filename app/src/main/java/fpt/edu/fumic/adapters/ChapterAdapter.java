package fpt.edu.fumic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.ChapterEntity;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    private List<ChapterEntity> list;

    public ChapterAdapter(List<ChapterEntity> list) {
        this.list = list;
    }

    public ChapterAdapter() {
    }

    public void setList(List<ChapterEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChapterEntity chapter = list.get(position);
        String chapterTitle = "Chapter " + chapter.getChapterNo() + ": " + chapter.getChapterTitle();
        holder.tvChapterNumber.setText(chapterTitle);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(chapter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvChapterNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_chapter);
            tvChapterNumber = itemView.findViewById(R.id.tv_chapter);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ChapterEntity chapter);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
