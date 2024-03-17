package fpt.edu.fumic.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.ChapterDAO;
import fpt.edu.fumic.database.entity.ChapterEntity;

public class ChapterRepository {

    private final ChapterDAO chapterDAO;

    public ChapterRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        chapterDAO = appDatabase.chapterDAO();
    }

    public String getChapterPerContent(int bookId, int chapterNo) {return chapterDAO.getContentPerChapter(bookId, chapterNo); }

    public List<ChapterEntity> getChapterTitleById(int bookId) {return chapterDAO.getChapterTitleById(bookId); }

    public String getChapterTitleByChapterNo(int id) {return chapterDAO.getChapterTitleByChapterNo(id); };
}

