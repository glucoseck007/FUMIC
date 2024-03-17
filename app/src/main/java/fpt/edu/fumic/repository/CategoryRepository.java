package fpt.edu.fumic.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.CategoryDAO;
import fpt.edu.fumic.database.entity.CategoryEntity;

public class CategoryRepository {
    CategoryDAO categoryDAO;
    public CategoryRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        categoryDAO = appDatabase.categoryDAO();
    }
    public LiveData<List<CategoryEntity>> getCategoryByID(int categoryId) {
        return categoryDAO.getCategoryByID(categoryId);
    }
    public LiveData<List<CategoryEntity>> getAllCategory() {
        return categoryDAO.loadAllCategories();
    }

}
