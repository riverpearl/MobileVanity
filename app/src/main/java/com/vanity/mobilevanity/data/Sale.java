package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class Sale {
    private long id;
    private Brand brand;
    private String endDay;
    private String startDay;

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

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
}
