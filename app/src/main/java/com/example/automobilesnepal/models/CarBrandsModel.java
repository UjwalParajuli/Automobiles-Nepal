package com.example.automobilesnepal.models;

import java.io.Serializable;

public class CarBrandsModel implements Serializable {
    String brand_logo, brand_name;

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

    public CarBrandsModel(String brand_logo, String brand_name) {
        this.brand_logo = brand_logo;
        this.brand_name = brand_name;
    }
}
