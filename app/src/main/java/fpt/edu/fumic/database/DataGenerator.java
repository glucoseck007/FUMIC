package fpt.edu.fumic.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.CategoryEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.BookRepository;

public class DataGenerator {

    public static void readBookCSV(Context context, String fileName, AppDatabase instance) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                int id = Integer.parseInt(data[0]);
                String title = data[1];
                String description = data[2];
                byte[] image;
                int categoryId = Integer.parseInt(data[4]);
                int rating = Integer.parseInt(data[5]);
                int noOfView = Integer.parseInt(data[6]);
                Date dateUpload = StringToDate(data[7]);
                int status = Integer.parseInt(data[8]);

                BookEntity book = new BookEntity();
                book.setId(id); book.setTitle(title); book.setDescription(description);
                book.setImage(image); book.setCategoryId(categoryId); book.setRating(rating);
                book.setStatus(status); book.setNoOfView(noOfView); book.setDateUpload(dateUpload);
                book.setContentURI(null);

                instance.bookDAO().insertBook(book);

            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ChapterEntity> readContent(Context context, Uri uri, int bookId) {
        List<ChapterEntity> list = new ArrayList<>();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                int chapterNo = Integer.parseInt(data[0]);
                String chapterTitle = data[1];
                String content = data[2];

                ChapterEntity chapter = new ChapterEntity();
                chapter.setChapterNo(chapterNo);
                chapter.setContent(content);
                chapter.setBookId(bookId);
                chapter.setChapterTitle(chapterTitle);

                list.add(chapter);
            }
            reader.close();
            inputStream.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void readCategoryCSV(Context context, String fileName, AppDatabase instance) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                int id = Integer.parseInt(data[0]);
                String name = data[1];

                CategoryEntity category = new CategoryEntity(id, name);
                instance.categoryDAO().insert(category);
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readUserCSV(Context context, String fileName, AppDatabase instance) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String id = data[0];
                String password = data[1];
                String name = data[2];
                Date dob = StringToDate(data[3]);
                int gender = data[4].equals("male") ? 1 : 2;
                String email = data[5];
                String phone = data[6];
                int role = data[7].equals("admin") ? 0 : data[7].equals("mod") ? 1 : 2;
                String notification = data[8];

                UserEntity user = new UserEntity(id, password, name, dob, gender, email, phone, role);

                instance.userDAO().insertUser(user);
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static String DateToString(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }

    public static Date StringToDate(String data) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.parse(data);
    }

}
