package fpt.edu.fumic.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.CategoryAdapter;
import fpt.edu.fumic.database.entity.CategoryEntity;
import fpt.edu.fumic.repository.CategoryRepository;


public class CategoryListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private CategoryRepository repository;
    private TextView tvempty;
    private ImageView ivAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);


        recyclerView = findViewById(R.id.recycler_view);
        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvempty = findViewById(R.id.empty_text_view);


        adapter.setOnCategoryClickListener(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(CategoryEntity category) {
                Intent intent = new Intent(CategoryListActivity.this, BookListActivity.class);
                intent.putExtra("categoryId", category.getId());
                startActivity(intent);
            }
        });


        repository = new CategoryRepository(this);
        LiveData<List<CategoryEntity>> categoriesLiveData = repository.getAllCategory();
        observeCategories(categoriesLiveData);
    }


    private void observeCategories(LiveData<List<CategoryEntity>> categoriesLiveData) {
        categoriesLiveData.observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categoryEntities) {
                if (categoryEntities.isEmpty()) {
                    tvempty.setVisibility(View.VISIBLE);
                    tvempty.setText("No category available");
                    adapter.clearCategories();
                } else {
                    tvempty.setVisibility(View.GONE);
                    adapter.setCategories(categoryEntities);
                }
            }
        });
    }
}
