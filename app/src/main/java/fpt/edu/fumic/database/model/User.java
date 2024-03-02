package fpt.edu.fumic.database.model;

import java.util.Date;

public interface User {
    public String getId();
    public String getPassword();
    public String getName();
    public Date getDob();
    public int getGender();
    public String getEmail();
    public String getPhone();
    public int getRole();
    public String getNotification();
}
