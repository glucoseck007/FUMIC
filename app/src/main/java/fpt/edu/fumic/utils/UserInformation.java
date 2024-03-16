package fpt.edu.fumic.utils;

import fpt.edu.fumic.database.entity.UserEntity;

public class UserInformation {
    private static UserInformation instance;
    private UserEntity user;
    private UserInformation(){}
    public static synchronized UserInformation getInstance() {
        if (instance == null) {
            instance = new UserInformation();
        }
        return instance;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
