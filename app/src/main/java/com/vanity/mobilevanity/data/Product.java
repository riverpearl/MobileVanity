package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class Product {
    private long id;
    private Brand brand;
    private int category;
    private int item;
    private String code;
    private String name;
    private String image;
    private int useBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUseBy() {
        return useBy;
    }

    public void setUseBy(int useBy) {
        this.useBy = useBy;
    }
}
