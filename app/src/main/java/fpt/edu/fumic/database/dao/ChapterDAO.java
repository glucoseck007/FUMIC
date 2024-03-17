package fpt.edu.fumic.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fpt.edu.fumic.database.entity.ChapterEntity;

@Dao
public interface ChapterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<ChapterEntity> list);

    @Query("SELECT content FROM CHAPTER WHERE bookId = :id and chapterNo = :chapterNo")
    String getContentPerChapter(int id, int chapterNo);

    @Query("SELECT chapterTitle FROM CHAPTER where bookId = :id and chapterNo = :chapterNo")
    String getChapterTitle(int id, int chapterNo);

}
