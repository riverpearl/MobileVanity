package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class CosmeticItem {
    private long id;
    private Cosmetic cosmetic;
    private String dateAdded;
    private int cosmeticTerm;
    private User owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cosmetic getCosmetic() {
        return cosmetic;
    }

    public void setCosmetic(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getCosmeticTerm() {
        return cosmeticTerm;
    }

    public void setCosmeticTerm(int cosmeticTerm) {
        this.cosmeticTerm = cosmeticTerm;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
