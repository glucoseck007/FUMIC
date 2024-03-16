package fpt.edu.fumic.database.model;

import java.util.Date;

public interface Book {
    public int getId();
    public String getTitle();
    public String getDescription();
    public byte[] getImage();
    public int getCategoryId();
    public int getRating();
    public int getNoOfView();
    public Date getDateUpload();
    public int getStatus();
    public String getContentURI();
}
