package fpt.edu.fumic.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fpt.edu.fumic.database.converter.DateConverter;
import fpt.edu.fumic.database.dao.AuthorDAO;
import fpt.edu.fumic.database.dao.BookDAO;
import fpt.edu.fumic.database.dao.CategoryDAO;
import fpt.edu.fumic.database.dao.UserDAO;
import fpt.edu.fumic.database.entity.AuthorEntity;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.CategoryEntity;
import fpt.edu.fumic.database.entity.OwnEntity;
import fpt.edu.fumic.database.entity.ReadEntity;
import fpt.edu.fumic.database.entity.UserEntity;

@Database(entities = {BookEntity.class,
        AuthorEntity.class,
        CategoryEntity.class,
        UserEntity.class,
        OwnEntity.class,
        ReadEntity.class}, version = 4,exportSchema = true)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();

    public abstract BookDAO bookDAO();

    public abstract AuthorDAO authorDAO();

    public abstract CategoryDAO categoryDAO();

    private static AppDatabase sInstance;
    private static final String DB_NAME = "FUMIC";

    public static synchronized AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return sInstance;
    }
    

}