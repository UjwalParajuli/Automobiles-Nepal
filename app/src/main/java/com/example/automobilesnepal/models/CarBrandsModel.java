package com.example.automobilesnepal.models;

import java.io.Serializable;

public class CarBrandsModel implements Serializable {
    private String brand_logo, brand_name;
    private int brand_id;

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public CarBrandsModel(String brand_logo, String brand_name, int brand_id) {
        this.brand_logo = brand_logo;
        this.brand_name = brand_name;
        this.brand_id = brand_id;
    }
}
