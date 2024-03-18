package fpt.edu.fumic.database;

import static com.airbnb.lottie.L.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import fpt.edu.fumic.database.converter.DateConverter;
import fpt.edu.fumic.database.dao.AuthorDAO;
import fpt.edu.fumic.database.dao.BookDAO;
import fpt.edu.fumic.database.dao.CategoryDAO;
import fpt.edu.fumic.database.dao.ChapterDAO;
import fpt.edu.fumic.database.dao.FavouriteDao;
import fpt.edu.fumic.database.dao.OwnDAO;
import fpt.edu.fumic.database.dao.UserDAO;
import fpt.edu.fumic.database.entity.AuthorEntity;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.CategoryEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.entity.FavouriteEntity;
import fpt.edu.fumic.database.entity.OwnEntity;
import fpt.edu.fumic.database.entity.ReadEntity;
import fpt.edu.fumic.database.entity.UserEntity;

@Database(entities = {BookEntity.class,
        AuthorEntity.class,
        CategoryEntity.class,
        ChapterEntity.class,
        UserEntity.class,
        OwnEntity.class,
        FavouriteEntity.class,
        ReadEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();

    public abstract BookDAO bookDAO();

    public abstract AuthorDAO authorDAO();

    public abstract CategoryDAO categoryDAO();

    public abstract OwnDAO ownDAO();
    public abstract ChapterDAO chapterDAO();
    public abstract FavouriteDao favouriteDao();

    private static AppDatabase sInstance;
    private static Context sAppContext;
    private static final String DB_NAME = "FUMIC";

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            sAppContext = context.getApplicationContext();
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    // Create a new database instance or get the existing one
                    sInstance = Room.databaseBuilder(sAppContext,
                                    AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(roomCallback) // Add a callback to handle database operations
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return sInstance;
    }

    // Callback to handle database operations
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                DataGenerator.readBookCSV(sAppContext, "book.csv", sInstance);
                DataGenerator.readAuthorsCSV(sAppContext, "author.csv", sInstance);
                DataGenerator.readCategoryCSV(sAppContext, "category.csv", sInstance);
                DataGenerator.readUserCSV(sAppContext, "user.csv", sInstance);
                DataGenerator.readChapterContent(sAppContext, "chapter.csv", sInstance);
                DataGenerator.readOwnCSV(sAppContext, "own.csv", sInstance);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                DataGenerator.readBookCSV(sAppContext, "book.csv", sInstance);
                DataGenerator.readAuthorsCSV(sAppContext, "author.csv", sInstance);
                DataGenerator.readCategoryCSV(sAppContext, "category.csv", sInstance);
                DataGenerator.readUserCSV(sAppContext, "user.csv", sInstance);
                DataGenerator.readChapterContent(sAppContext, "chapter.csv", sInstance);
                DataGenerator.readOwnCSV(sAppContext, "own.csv", sInstance);
            });
        }
    };

    static final Migration MIGRATION_1_2 = new Migration(1, 2) { // From version 1 to version 2
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            // Remove the table
            db.execSQL("DROP TABLE IF EXISTS BOOK");
            db.execSQL("DROP TABLE IF EXISTS AUTHOR");
            db.execSQL("DROP TABLE IF EXISTS CATEGORY");
            db.execSQL("DROP TABLE IF EXISTS USER");
            db.execSQL("DROP TABLE IF EXISTS OWN");
            db.execSQL("DROP TABLE IF EXISTS READ");
            db.execSQL("DROP TABLE IF EXISTS FAVOURITE");
            db.execSQL("DROP TABLE IF EXISTS CHAPTER");// Use the right table name

            // OR: We could update it, by using an ALTER query

            // OR: If needed, we can create the table again with the required settings
            // database.execSQL("CREATE TABLE IF NOT EXISTS my_table (id INTEGER, PRIMARY KEY(id), ...)")
        }
    };
}
