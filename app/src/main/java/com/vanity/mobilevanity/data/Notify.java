package com.vanity.mobilevanity.data;

import java.util.Date;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class Notify {
    private long id;
    private String type;
    private long beautyTipId;
    private String message;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getBeautyTipId() {
        return beautyTipId;
    }

    public void setBeautyTipId(long beautyTipId) {
        this.beautyTipId = beautyTipId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
