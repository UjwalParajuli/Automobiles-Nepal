package com.example.automobilesnepal.models;

import java.io.Serializable;

public class UsedCarsModel implements Serializable {
    private int used_car_id, car_model_id, posted_by, no_of_previous_owners, total_kilometers;
    private Double selling_car_price;
    private String registered_year, used_car_color, is_verified, selling_location, used_car_photo, posted_date, car_model_name, brand_name;

    public UsedCarsModel(int used_car_id, int car_model_id, int posted_by, int no_of_previous_owners, int total_kilometers, Double selling_car_price, String registered_year, String used_car_color, String is_verified, String selling_location, String used_car_photo, String posted_date, String car_model_name, String brand_name) {
        this.used_car_id = used_car_id;
        this.car_model_id = car_model_id;
        this.posted_by = posted_by;
        this.no_of_previous_owners = no_of_previous_owners;
        this.total_kilometers = total_kilometers;
        this.selling_car_price = selling_car_price;
        this.registered_year = registered_year;
        this.used_car_color = used_car_color;
        this.is_verified = is_verified;
        this.selling_location = selling_location;
        this.used_car_photo = used_car_photo;
        this.posted_date = posted_date;
        this.car_model_name = car_model_name;
        this.brand_name = brand_name;
    }

    public int getUsed_car_id() {
        return used_car_id;
    }

    public void setUsed_car_id(int used_car_id) {
        this.used_car_id = used_car_id;
    }

    public int getCar_model_id() {
        return car_model_id;
    }

    public void setCar_model_id(int car_model_id) {
        this.car_model_id = car_model_id;
    }

    public int getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(int posted_by) {
        this.posted_by = posted_by;
    }

    public int getNo_of_previous_owners() {
        return no_of_previous_owners;
    }

    public void setNo_of_previous_owners(int no_of_previous_owners) {
        this.no_of_previous_owners = no_of_previous_owners;
    }

    public int getTotal_kilometers() {
        return total_kilometers;
    }

    public void setTotal_kilometers(int total_kilometers) {
        this.total_kilometers = total_kilometers;
    }

    public Double getSelling_car_price() {
        return selling_car_price;
    }

    public void setSelling_car_price(Double selling_car_price) {
        this.selling_car_price = selling_car_price;
    }

    public String getRegistered_year() {
        return registered_year;
    }

    public void setRegistered_year(String registered_year) {
        this.registered_year = registered_year;
    }

    public String getUsed_car_color() {
        return used_car_color;
    }

    public void setUsed_car_color(String used_car_color) {
        this.used_car_color = used_car_color;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }

    public String getSelling_location() {
        return selling_location;
    }

    public void setSelling_location(String selling_location) {
        this.selling_location = selling_location;
    }

    public String getUsed_car_photo() {
        return used_car_photo;
    }

    public void setUsed_car_photo(String used_car_photo) {
        this.used_car_photo = used_car_photo;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    public String getCar_model_name() {
        return car_model_name;
    }

    public void setCar_model_name(String car_model_name) {
        this.car_model_name = car_model_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }
}
