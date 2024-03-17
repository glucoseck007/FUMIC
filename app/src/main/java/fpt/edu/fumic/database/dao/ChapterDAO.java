package fpt.edu.fumic.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.database.entity.ChapterEntity;

@Dao
public interface ChapterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<ChapterEntity> list);

    @Update
    int updateContent(List<ChapterEntity> list);

    @Query("SELECT content FROM CHAPTER WHERE bookId = :id and chapterNo = :chapterNo")
    String getContentPerChapter(int id, int chapterNo);

    @Query("SELECT * FROM CHAPTER where bookId = :id")
    List<ChapterEntity> getChapterTitleById(int id);

    @Query("SELECT chapterTitle FROM CHAPTER where chapterNo = :id")
    String getChapterTitleByChapterNo(int id);

    @Query("DELETE FROM CHAPTER where bookId = :id")
    void deleteChapterByBookId(int id);
}
