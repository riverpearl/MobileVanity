package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class CommentItem implements AlertItem{
    private String User;

    public CommentItem(String user) {
        this.User = user;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
