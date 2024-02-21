package fpt.edu.fumic.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.model.Category;

public class FumicDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "FumicDB";
    public static final int DB_VERSION = 1;
    public Context context;

    public FumicDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static final String TABLE_USER = "CREATE TABLE USER(" +
            "Id TEXT PRIMARY KEY, " +
            "Password TEXT NOT NULL, " +
            "Name TEXT NOT NULL, " +
            "Age INTEGER, " +
            "Email TEXT NOT NULL, " +
            "Phone TEXT, " +
            "Role INTEGER NOT NULL, " +
            "Notification TEXT" + ")";

    public static final String TABLE_BOOK = "CREATE TABLE BOOK(" +
            "Id INTEGER PRIMARY KEY, " +
            "Title TEXT NOT NULL, " +
            "NoOfChapter INTEGER NOT NULL, " +
            "Description TEXT, " +
            "CategoryId INTEGER REFERENCES CATEGORY(Id), " +
            "Rating INTEGER, " +
            "NoOfView INTEGER, " +
            "DateUpload DATE, " +
            "Status INTEGER, " +
            "UserId TEXT " + ")";

    public static final String TABLE_AUTHOR = "CREATE TABLE AUTHOR(" +
            "Id INTEGER PRIMARY KEY, " +
            "Name TEXT, " +
            "Age INTEGER, " +
            "Information TEXT" + ")";

    public static final String TABLE_CATEGORY = "CREATE TABLE CATEGORY(" +
            "Id INTEGER PRIMARY KEY, " +
            "Name TEXT NOT NULL" + ")";


    public static final String TABLE_OWN = "CREATE TABLE OWN(" +
            "AuthorId INTEGER REFERENCES AUTHOR(AuthorId), " +
            "BookId INTEGER REFERENCES BOOK(BookId), " +
            "PRIMARY KEY(AuthorId, BookId) " + ")";

    public static final String TABLE_FAVOURITE = "CREATE TABLE FAVOURITE(" +
            "UserId TEXT REFERENCES USER(Id), " +
            "BookId INTEGER REFERENCES BOOK(Id)" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create TABLE USER
        db.execSQL(TABLE_USER);
        //Crate TABLE CATEGORY
        db.execSQL(TABLE_CATEGORY);
        //Create TABLE BOOK
        db.execSQL(TABLE_BOOK);
        //Create TABLE AUTHOR
        db.execSQL(TABLE_AUTHOR);
        //Create TABLE OWN
        db.execSQL(TABLE_OWN);
        //Create TABLE FAVOURITE
        db.execSQL(TABLE_FAVOURITE);

        String insertAuthor = "INSERT INTO CATEGORY(Id, Name) VALUES (?, ?)";
        db.execSQL(insertAuthor, new Object[]{1, "Anime"});

        Log.d("FumicDB", "Database created successfully");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS USER");
            db.execSQL("DROP TABLE IF EXISTS CATEGORY");
            db.execSQL("DROP TABLE IF EXISTS BOOK");
            db.execSQL("DROP TABLE IF EXISTS AUHOR");
            db.execSQL("DROP TABLE IF EXISTS OWN");
            db.execSQL("DROP TABLE IF EXISTS FAVOURITE");

            onCreate(db);
        }
    }

}
