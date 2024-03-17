package fpt.edu.fumic.repository;

import android.content.Context;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.ChapterDAO;

public class ChapterRepository {

    private final ChapterDAO chapterDAO;

    public ChapterRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        chapterDAO = appDatabase.chapterDAO();
    }

}

