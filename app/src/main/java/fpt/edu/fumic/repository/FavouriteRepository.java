package fpt.edu.fumic.repository;

import android.content.Context;

import java.util.List;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.FavouriteDao;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.FavouriteEntity;

/*
 * luong_123
 */
public class FavouriteRepository {

    private final FavouriteDao favouriteDao;

    public FavouriteRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        favouriteDao = appDatabase.favouriteDao();
    }


    public void addFavourite(FavouriteEntity favouriteEntities) {
        favouriteDao.insertFavourite(favouriteEntities);
    }
    public void deleteFavourite(int bookId) {
        favouriteDao.deleteBookById(bookId);
    }
    public boolean isBookInFavourites(String userId, int bookId) { 
        return favouriteDao.isBookInFavourites(userId, bookId) > 0;
    }

    public List<BookEntity> getFavourite(String userId, int limit, int offset) {
        return favouriteDao.getFavouriteList(userId, limit, offset);
    }


}
