package fpt.edu.fumic.model;

import java.sql.Date;

public class Book {

    private int id;
    private String title;
    private int noOfChapter;
    private String description;
    private int categoryId;
    private int rating;
    private int view;
    private Date dateUpload;
    private int status;
    private int userId;

    public Book() {};

    public Book(int id, String title, int noOfChapter, String description, int categoryId, int rating, int view, Date dateUpload, int status, int userId) {
        this.id = id;
        this.title = title;
        this.noOfChapter = noOfChapter;
        this.description = description;
        this.categoryId = categoryId;
        this.rating = rating;
        this.view = view;
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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
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
