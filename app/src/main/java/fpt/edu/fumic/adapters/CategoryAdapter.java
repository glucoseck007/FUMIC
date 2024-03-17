package fpt.edu.fumic.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.CategoryEntity;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private List<CategoryEntity> categoryList;
    private OnCategoryClickListener onCategoryClickListener;








    public interface OnCategoryClickListener {
        void onCategoryClick(CategoryEntity category);
    }


    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.onCategoryClickListener = listener;
    }


    public CategoryAdapter() {
        this.categoryList = new ArrayList<>();
    }


    public void setCategories(List<CategoryEntity> categories) {
        this.categoryList = categories;
        notifyDataSetChanged();
    }


    public void clearCategories() {
        this.categoryList.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryEntity category = categoryList.get(position);
        holder.categoryId.setText(String.valueOf(category.getId()));
        holder.categoryName.setText(category.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCategoryClickListener != null) {
                    onCategoryClickListener.onCategoryClick(category);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryId, categoryName;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryId = itemView.findViewById(R.id.category_id);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}

