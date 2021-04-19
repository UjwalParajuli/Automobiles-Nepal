package com.example.automobilesnepal.models;

import java.io.Serializable;

public class BikeBrandsModel implements Serializable {
    private int bike_brand_id;
    private String brand_name, brand_logo;

    public BikeBrandsModel(int bike_brand_id, String brand_name, String brand_logo) {
        this.bike_brand_id = bike_brand_id;
        this.brand_name = brand_name;
        this.brand_logo = brand_logo;
    }

    public int getBike_brand_id() {
        return bike_brand_id;
    }

    public void setBike_brand_id(int bike_brand_id) {
        this.bike_brand_id = bike_brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    @Override
    public String toString() {
        return brand_name;
    }
}
