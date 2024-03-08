package fpt.edu.fumic.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import fpt.edu.fumic.database.model.Book;

@Entity(tableName = "BOOK")
public class BookEntity implements Book {

    @PrimaryKey
    @NonNull
    private int id;
    private String title;
    private int noOfChapter;
    private String description;
    private String imageURL;
    private int categoryId;
    private int rating;
    private int noOfView;
    private Date dateUpload;
    private int status;
    private int authorId;

    public BookEntity() {};

    public BookEntity(int id, String title, int noOfChapter, String description, String imageURL, int categoryId, int rating, int noOfView, Date dateUpload, int status) {
        this.id = id;
        this.title = title;
        this.noOfChapter = noOfChapter;
        this.description = description;
        this.imageURL = imageURL;
        this.categoryId = categoryId;
        this.rating = rating;
        this.noOfView = noOfView;
        this.dateUpload = dateUpload;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getNoOfChapter() {
        return noOfChapter;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getRating() {
        return rating;
    }

    public int getNoOfView() {
        return noOfView;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public int getStatus() {
        return status;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNoOfChapter(int noOfChapter) {
        this.noOfChapter = noOfChapter;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setNoOfView(int noOfView) {
        this.noOfView = noOfView;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
