package com.vanity.mobilevanity.data;

import android.graphics.drawable.Drawable;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BannerData {
    private Drawable image;
    private String url;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
