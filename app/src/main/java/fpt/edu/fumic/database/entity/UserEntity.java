package fpt.edu.fumic.database.entity;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import fpt.edu.fumic.database.model.User;

@Entity(tableName = "USER")
public class UserEntity implements User, Serializable {

//    public static final String MATCHES_PHONENUMBER = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";
    public static final String MATCHES_PHONENUMBER = "^0\\d{9}$";
    public static final String MATCHES_EMAIL = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @PrimaryKey
    @NonNull
    private String id;
    private String password;
    private String name;
    private Date dob;
    private int gender;
    private String email;
    private String phone;
    private int role;
    private String notification;

    public UserEntity() {};

    public UserEntity(@NonNull String id, String password, String name, Date dob, int gender, String email, String phone, int role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public int getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getRole() {
        return role;
    }

    public String getNotification() {
        return notification;
    }
}
