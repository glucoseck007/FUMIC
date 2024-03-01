package fpt.edu.fumic.model;

import java.sql.Date;

public class Book {

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
    private int userId;

    public Book() {};

    public Book(int id, String title, int noOfChapter, String description, String imageURL, int categoryId, int rating, int noOfView, Date dateUpload, int status, int userId) {
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
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoOfChapter() {
        return noOfChapter;
    }

    public void setNoOfChapter(int noOfChapter) {
        this.noOfChapter = noOfChapter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getNoOfView() {
        return noOfView;
    }

    public void setNoOfView(int noOfView) {
        this.noOfView = noOfView;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
