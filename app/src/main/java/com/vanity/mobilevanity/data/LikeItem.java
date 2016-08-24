package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class LikeItem implements AlertItem{
    private String User;

    public LikeItem(String user) {
        this.User = user;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
