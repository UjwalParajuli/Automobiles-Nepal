package com.example.automobilesnepal.models;

import java.io.Serializable;

public class BikesModel implements Serializable {
    private int bike_model_id, bike_brands_id;
    private String model_name, mileage, displacement, engine_type, no_of_cylinders, max_power, max_torque, front_brake, rear_brake, fuel_capacity, body_type, price, description, bike_photo, brand_logo, brand_name, bike_review_video_link, bike_color;

    public BikesModel(int bike_model_id, int bike_brands_id, String model_name, String mileage, String displacement, String engine_type, String no_of_cylinders, String max_power, String max_torque, String front_brake, String rear_brake, String fuel_capacity, String body_type, String price, String description, String bike_photo, String brand_logo, String brand_name, String bike_review_video_link, String bike_color) {
        this.bike_model_id = bike_model_id;
        this.bike_brands_id = bike_brands_id;
        this.model_name = model_name;
        this.mileage = mileage;
        this.displacement = displacement;
        this.engine_type = engine_type;
        this.no_of_cylinders = no_of_cylinders;
        this.max_power = max_power;
        this.max_torque = max_torque;
        this.front_brake = front_brake;
        this.rear_brake = rear_brake;
        this.fuel_capacity = fuel_capacity;
        this.body_type = body_type;
        this.price = price;
        this.description = description;
        this.bike_photo = bike_photo;
        this.brand_logo = brand_logo;
        this.brand_name = brand_name;
        this.bike_review_video_link = bike_review_video_link;
        this.bike_color = bike_color;

    }

    public int getBike_model_id() {
        return bike_model_id;
    }

    public void setBike_model_id(int bike_model_id) {
        this.bike_model_id = bike_model_id;
    }

    public int getBike_brands_id() {
        return bike_brands_id;
    }

    public void setBike_brands_id(int bike_brands_id) {
        this.bike_brands_id = bike_brands_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }

    public String getNo_of_cylinders() {
        return no_of_cylinders;
    }

    public void setNo_of_cylinders(String no_of_cylinders) {
        this.no_of_cylinders = no_of_cylinders;
    }

    public String getMax_power() {
        return max_power;
    }

    public void setMax_power(String max_power) {
        this.max_power = max_power;
    }

    public String getMax_torque() {
        return max_torque;
    }

    public void setMax_torque(String max_torque) {
        this.max_torque = max_torque;
    }

    public String getFront_brake() {
        return front_brake;
    }

    public void setFront_brake(String front_brake) {
        this.front_brake = front_brake;
    }

    public String getRear_brake() {
        return rear_brake;
    }

    public void setRear_brake(String rear_brake) {
        this.rear_brake = rear_brake;
    }

    public String getFuel_capacity() {
        return fuel_capacity;
    }

    public void setFuel_capacity(String fuel_capacity) {
        this.fuel_capacity = fuel_capacity;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBike_photo() {
        return bike_photo;
    }

    public void setBike_photo(String bike_photo) {
        this.bike_photo = bike_photo;
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

    public String getBike_review_video_link() {
        return bike_review_video_link;
    }

    public void setBike_review_video_link(String bike_review_video_link) {
        this.bike_review_video_link = bike_review_video_link;
    }

    public String getBike_color() {
        return bike_color;
    }

    public void setBike_color(String bike_color) {
        this.bike_color = bike_color;
    }

    @Override
    public String toString() {
        return model_name;
    }
}
