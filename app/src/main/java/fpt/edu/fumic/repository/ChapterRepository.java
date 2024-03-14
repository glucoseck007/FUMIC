package fpt.edu.fumic.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prm_project.dal.BookDatabase;
import com.example.prm_project.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterRepository {
    private BookDatabase bookDatabase;
    public ChapterRepository(Context context) {
        bookDatabase = new BookDatabase(context);
    }

    public List<Chapter> getChaptersByBookId(int bookId) {
        List<Chapter> chapterList = new ArrayList<>();
        SQLiteDatabase db = bookDatabase.getReadableDatabase();

        // Truy vấn cơ sở dữ liệu để lấy danh sách chương của cuốn sách có bookId
        String[] projection = {
                "ChapterId",
                "ChapterNumber",
                "ChapterContent"
        };
        String selection = "BookId = ?";
        String[] selectionArgs = { String.valueOf(bookId) };

        Cursor cursor = db.query(
                "CHAPTER",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Xử lý dữ liệu trả về từ cơ sở dữ liệu và thêm vào danh sách chương
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int chapterId = cursor.getInt(cursor.getColumnIndexOrThrow("ChapterId"));
                String chapterNumber = cursor.getString(cursor.getColumnIndexOrThrow("ChapterNumber"));
                String chapterContent = cursor.getString(cursor.getColumnIndexOrThrow("ChapterContent"));
                Chapter chapter = new Chapter(chapterId, bookId, chapterNumber, chapterContent);
                chapterList.add(chapter);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return chapterList;
    }
}

