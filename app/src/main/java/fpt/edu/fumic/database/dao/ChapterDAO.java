package fpt.edu.fumic.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fpt.edu.fumic.database.entity.ChapterEntity;

@Dao
public interface ChapterDAO {

    @Insert
    List<Long> insert(List<ChapterEntity> list);

    @Query("SELECT content FROM CHAPTER WHERE bookId = :id and chapterNo = :chapterNo")
    String getContentPerChapter(int id, int chapterNo);

}
