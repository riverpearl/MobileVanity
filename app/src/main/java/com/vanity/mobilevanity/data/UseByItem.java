package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class UseByItem implements AlertItem{
    private String cosmetic;
    private int useby;

    public UseByItem(String cosmetic, int useby) {
        this.cosmetic = cosmetic;
        this.useby = useby;
    }

    public String getCosmetic() {
        return cosmetic;
    }

    public void setCosmetic(String cosmetic) {
        this.cosmetic = cosmetic;
    }

    public int getUseby() {
        return useby;
    }

    public void setUseby(int useby) {
        this.useby = useby;
    }
}
