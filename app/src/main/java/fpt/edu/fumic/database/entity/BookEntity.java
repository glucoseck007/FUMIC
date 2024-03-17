package fpt.edu.fumic.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import fpt.edu.fumic.database.model.Book;

@Entity(tableName = "BOOK")
public class BookEntity implements Book , Serializable {
    @PrimaryKey
    @NonNull
    private int id;
    private String title;
    private String description;
    private byte[] image;
    private int categoryId;
    private int rating;
    private int noOfView;
    private Date dateUpload;
    private int status;
    private String contentURI;

    public BookEntity() {};

    public BookEntity(int id, String title, String description, byte[] image, int categoryId, int rating, int noOfView, Date dateUpload, int status, String contentURI) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.categoryId = categoryId;
        this.rating = rating;
        this.noOfView = noOfView;
        this.dateUpload = dateUpload;
        this.status = status;
        this.contentURI = contentURI;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getNoOfChapter() {
        return 0;
    }


    public String getDescription() {
        return description;
    }

    @Override
    public String getImageURL() {
        return null;
    }

    public byte[] getImage() {
        return image;
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

    public String getContentURI() {return contentURI;}

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public void setContentURI(String contentURI) {this.contentURI = contentURI; }

}
