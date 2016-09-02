package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class Notify {
    private long id;
    private String type;
    private BeautyTipId beautyTipId;
    private long cosmeticItemId;
    private String message;
    private String date;

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

    public BeautyTipId getBeautyTipId() {
        return beautyTipId;
    }

    public void setBeautyTipId(BeautyTipId beautyTipId) {
        this.beautyTipId = beautyTipId;
    }

    public long getCosmeticItemId() {
        return cosmeticItemId;
    }

    public void setCosmeticItemId(long cosmeticItemId) {
        this.cosmeticItemId = cosmeticItemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
