package com.example.automobilesnepal.models;

import java.io.Serializable;

public class UsedBikesModel implements Serializable {
    private int used_bike_id, bike_model_id, no_of_previous_owner, total_kilometers, posted_by;
    private double selling_price;
    private String registered_year, used_bike_color, is_verified, selling_location, used_bike_photo, posted_date, bike_model_name, brand_name;

    public UsedBikesModel(int used_bike_id, int bike_model_id, int no_of_previous_owner, int total_kilometers, int posted_by, double selling_price, String registered_year, String used_bike_color, String is_verified, String selling_location, String used_bike_photo, String posted_date, String bike_model_name, String brand_name) {
        this.used_bike_id = used_bike_id;
        this.bike_model_id = bike_model_id;
        this.no_of_previous_owner = no_of_previous_owner;
        this.total_kilometers = total_kilometers;
        this.posted_by = posted_by;
        this.selling_price = selling_price;
        this.registered_year = registered_year;
        this.used_bike_color = used_bike_color;
        this.is_verified = is_verified;
        this.selling_location = selling_location;
        this.used_bike_photo = used_bike_photo;
        this.posted_date = posted_date;
        this.bike_model_name = bike_model_name;
        this.brand_name = brand_name;
    }

    public int getUsed_bike_id() {
        return used_bike_id;
    }

    public void setUsed_bike_id(int used_bike_id) {
        this.used_bike_id = used_bike_id;
    }

    public int getBike_model_id() {
        return bike_model_id;
    }

    public void setBike_model_id(int bike_model_id) {
        this.bike_model_id = bike_model_id;
    }

    public int getNo_of_previous_owner() {
        return no_of_previous_owner;
    }

    public void setNo_of_previous_owner(int no_of_previous_owner) {
        this.no_of_previous_owner = no_of_previous_owner;
    }

    public int getTotal_kilometers() {
        return total_kilometers;
    }

    public void setTotal_kilometers(int total_kilometers) {
        this.total_kilometers = total_kilometers;
    }

    public int getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(int posted_by) {
        this.posted_by = posted_by;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }

    public String getRegistered_year() {
        return registered_year;
    }

    public void setRegistered_year(String registered_year) {
        this.registered_year = registered_year;
    }

    public String getUsed_bike_color() {
        return used_bike_color;
    }

    public void setUsed_bike_color(String used_bike_color) {
        this.used_bike_color = used_bike_color;
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

    public String getUsed_bike_photo() {
        return used_bike_photo;
    }

    public void setUsed_bike_photo(String used_bike_photo) {
        this.used_bike_photo = used_bike_photo;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    public String getBike_model_name() {
        return bike_model_name;
    }

    public void setBike_model_name(String bike_model_name) {
        this.bike_model_name = bike_model_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }
}
